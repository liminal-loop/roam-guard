package com.roamguard.app.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.roamguard.app.MainActivity
import com.roamguard.app.R
import com.roamguard.common.util.Constants
import com.roamguard.domain.repository.NetworkRepository
import com.roamguard.domain.repository.SettingsRepository
import com.roamguard.domain.repository.WhitelistRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RoamingForegroundService : LifecycleService() {

    @Inject lateinit var networkRepository: NetworkRepository
    @Inject lateinit var whitelistRepository: WhitelistRepository
    @Inject lateinit var settingsRepository: SettingsRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var scanJob: Job? = null

    override fun onCreate() {
        super.onCreate()
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
        startMonitoring()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onDestroy() {
        scanJob?.cancel()
        scope.cancel()
        super.onDestroy()
    }

    private fun startMonitoring() {
        scanJob?.cancel()
        scanJob = scope.launch {
            while (isActive) {
                try {
                    val interval = settingsRepository.scanIntervalSeconds.first()
                    checkRoamingState()
                    delay(interval * 1000L)
                } catch (e: Exception) {
                    delay(Constants.DEFAULT_SCAN_INTERVAL * 1000L)
                }
            }
        }
    }

    private suspend fun checkRoamingState() {
        val currentNetwork = networkRepository.getCurrentNetwork() ?: return
        val homeCountry = whitelistRepository.homeCountry.first() ?: return

        if (!currentNetwork.isRoaming && currentNetwork.mcc == homeCountry.mcc) {
            return
        }

        if (currentNetwork.isWhitelisted) {
            return
        }

        if (currentNetwork.mcc != homeCountry.mcc && currentNetwork.isRoaming) {
            networkRepository.recheckAndReconnect()
        }
    }
}
