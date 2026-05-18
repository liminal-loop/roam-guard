package com.roamguard.app.service

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.telephony.PhoneStateListener
import android.telephony.TelephonyDisplayInfo
import android.telephony.TelephonyManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleService
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.roamguard.app.MainActivity
import com.roamguard.app.R
import com.roamguard.app.work.RoamingCheckWorker
import com.roamguard.common.system.SystemNetworkController
import com.roamguard.common.util.Constants
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoamingForegroundService : LifecycleService() {

    @Inject lateinit var whitelistRepository: WhitelistRepository
    @Inject lateinit var settingsRepository: SettingsRepository
    @Inject lateinit var systemController: SystemNetworkController

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var telephonyManager: TelephonyManager? = null
    private var phoneStateListener: PhoneStateListener? = null

    override fun onCreate() {
        super.onCreate()
        telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        startForeground(Constants.NOTIFICATION_ID, notification)
        registerPhoneStateListener()
        schedulePeriodicCheck()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onDestroy() {
        unregisterPhoneStateListener()
        WorkManager.getInstance(this).cancelUniqueWork(Constants.WORK_NAME_ROAMING_CHECK)
        scope.cancel()
        super.onDestroy()
    }

    private fun registerPhoneStateListener() {
        unregisterPhoneStateListener()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val listener = object : PhoneStateListener() {
            override fun onDisplayInfoChanged(telephonyDisplayInfo: TelephonyDisplayInfo) {
                onNetworkChanged()
            }
        }
        phoneStateListener = listener
        telephonyManager?.listen(listener, PhoneStateListener.LISTEN_DISPLAY_INFO_CHANGED)
    }

    private fun unregisterPhoneStateListener() {
        phoneStateListener?.let {
            telephonyManager?.listen(it, PhoneStateListener.LISTEN_NONE)
        }
        phoneStateListener = null
    }

    private fun schedulePeriodicCheck() {
        scope.launch {
            val interval = settingsRepository.scanIntervalSeconds.first()

            val request = PeriodicWorkRequestBuilder<RoamingCheckWorker>(
                interval.toLong(), TimeUnit.SECONDS
            ).build()

            WorkManager.getInstance(this@RoamingForegroundService).enqueueUniquePeriodicWork(
                Constants.WORK_NAME_ROAMING_CHECK,
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }

    private fun onNetworkChanged() {
        scope.launch {
            val country = systemController.getCurrentNetworkCountry() ?: return@launch
            val homeCountry = whitelistRepository.homeCountry.first() ?: return@launch

            if (country.equals(homeCountry.countryCode, ignoreCase = true)) {
                return@launch
            }

            val isWhitelisted = whitelistRepository.whitelist.first().any {
                it.countryCode.equals(country, ignoreCase = true)
            }

            if (isWhitelisted) return@launch

            systemController.forceManualNetworkSelection(null)
        }
    }
}
