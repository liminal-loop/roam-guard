package com.roamguard.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.roamguard.common.util.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RoamGuardApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            Constants.NOTIFICATION_CHANNEL_ID,
            getString(R.string.notification_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(R.string.notification_channel_description)
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
