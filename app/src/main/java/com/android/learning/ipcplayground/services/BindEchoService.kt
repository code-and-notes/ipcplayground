package com.android.learning.ipcplayground.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BindEchoService: Service() {

    private val tag = "BindEchoService"
    private val echoBinder : IBinder = LocalEchoBinder()

    override fun onBind(p0: Intent?): IBinder? {
        Log.w(tag, "onBind() called with: p0 = $p0")
        return echoBinder
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
        Log.d(tag, "onUnbind() called with: intent = $intent")
        return super.onUnbind(intent)
    }

    val getRandomNumber : Int
        get() = (1..100).random()

    inner class LocalEchoBinder : Binder() {
        fun getService(): BindEchoService = this@BindEchoService
    }

}