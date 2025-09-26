package com.android.learning.ipcplayground.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.android.learning.ipcplayground.Proto

class MessengerService: Service() {
    val tag = "MessengerService"
    private lateinit var messenger: Messenger
    override fun onBind(p0: android.content.Intent?): android.os.IBinder? {
        messenger = Messenger(handler)
        Log.v(tag, "MessengerService onBind")
        return messenger.binder
    }

    override fun onCreate() {
        Log.v(tag, "MessengerService onCreate")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.v(tag, "MessengerService onDestroy")
        super.onDestroy()
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        Log.v(tag, "MessengerService onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.v(tag, "MessengerService onUnbind")
        return super.onUnbind(intent)
    }

    val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                Proto.C_PING -> {
                    Log.v("MessengerService", "Message received: from client ${msg.data}")
                    val message = Message.obtain(null, Proto.S_PONG)
                    message.replyTo = messenger
                    msg.replyTo?.send(message)
                }
                else -> {
                    Log.v("MessengerService", "Unknown message received: ${msg.what}")
                }
            }
            super.handleMessage(msg)
        }
    }
}