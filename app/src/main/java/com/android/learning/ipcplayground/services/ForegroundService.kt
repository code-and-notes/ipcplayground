package com.android.learning.ipcplayground.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class ForegroundService: Service() {
    val channelId = "ForegroundServiceChannel"
    val tag = "ForegroundService"
    override fun onBind(p0: android.content.Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.v(tag, "onCreate() called")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

    }

    override fun onDestroy() {
        Log.v(tag, "onDestroy() called")
        super.onDestroy()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.v(tag, "onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId")
        Log.d(tag, "Foreground Service Started.")
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service")
            .setContentText("This is a foreground service running.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        startForeground(100,notification)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
}