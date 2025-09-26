package com.android.learning.ipcplayground.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class EchoService : Service() {

    private val tag = "EchoService"

    override fun onBind(p0: Intent?): IBinder? {
        Log.d(tag, "onDestroy() called")
        return null
    }

    override fun onCreate() {
        Log.d(tag, "onCreate() called")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(tag, "onDestroy() called")
        super.onDestroy()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.d(tag, "onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(tag, "onDestroy() called")
        return super.onUnbind(intent)
    }
}