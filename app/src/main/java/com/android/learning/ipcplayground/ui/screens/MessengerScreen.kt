package com.android.learning.ipcplayground.ui.screens

import android.R.attr.onClick
import android.R.id.message
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.learning.ipcplayground.Proto
import com.android.learning.ipcplayground.services.BindEchoService
import com.android.learning.ipcplayground.services.MessengerService
import kotlin.jvm.java

@Composable
fun MessengerScreen() {
    val context = LocalContext.current

    val activity = remember(context) { context.findActivity() }
    val intent = Intent(activity, MessengerService::class.java)
    var messengerService: Messenger? by remember { mutableStateOf(null) }
    var isBound: Boolean by remember { mutableStateOf(false) }
    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            // Handle messages from the service here
            val data = msg.data
            msg.replyTo = messengerService
            val message = Message.obtain(null, Proto.C_PING)
            msg.replyTo?.send(message)
            // Update UI state as needed
            Log.w("BindScreen", "Message received from service: ${msg.what}, data: $data")
        }
    }
    val clientMessenger = Messenger(handler)

    // When sending a message:
    val message = Message.obtain(null, Proto.C_PING)
    message.replyTo = clientMessenger
    messengerService?.send(message)

    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: android.content.ComponentName?, service: IBinder?) {
                Log.w("BindScreen", "onServiceConnected() called with: name = $name, service = $service")
                messengerService = Messenger(service)
            }

            override fun onServiceDisconnected(name: android.content.ComponentName?) {
                messengerService = null
                Log.w("BindScreen", "onServiceDisconnected() called with: name = $name")
            }

        }}

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(
            onClick = {context.bindService(intent,connection,Context.BIND_AUTO_CREATE)
            isBound = true}
        ) {
            Text("bind to  messenger service")
        }
        Button(
            onClick = {
                if (isBound) {
                    val message = Message.obtain(null, Proto.C_PING)
                    message.replyTo = clientMessenger
                    val bundle = Bundle()
                    bundle.putInt("status", Proto.C_FETCH_STATUS)
                    message.data = bundle
                    messengerService?.send(message)
                }
            }
        ) {
            Text("Send message to messenger service")
        }
    }
}