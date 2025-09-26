package com.android.learning.echo_server

import android.R.attr.name
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.PendingIntentCompat.send
import com.android.learning.echo_server.ui.theme.IpcplaygroundTheme

class MainActivity : ComponentActivity() {

    private lateinit var messenger: Messenger
    private var isBound : Boolean = false

    private val tag = "MainActivity"
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: android.content.ComponentName?, service: android.os.IBinder?) {
            messenger = Messenger(service)
            isBound = true
            // Not used in this example
        }

        override fun onServiceDisconnected(name: android.content.ComponentName?) {
            isBound = false
            // Not used in this example
        }
    }
    private val handler = Handler(Looper.getMainLooper()) { msg ->
        Log.v(tag, "Sending message to service: ${msg.data}")
        when (msg.what) {
            Proto.C_PING -> {
                val data = msg.data
                Log.v(tag, "Message received from service: ${data}")
                true
            }

            else -> {
                Log.v(tag, "Unknown message received from service: ${msg.what}")
                false
            }
        }
    }
    private var replyMessenger = Messenger(handler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IpcplaygroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        {sendPing("Hello from echo-server")}
                    )
                }
            }
            val intent = Intent().apply{
                component =  ComponentName("com.android.learning.ipcplayground", "com.android.learning.ipcplayground.services.MessengerService")
            }
            bindService(intent,connection, BIND_AUTO_CREATE)

        }
    }
    private fun sendPing(s: String) {
        Log.d(tag, "sendPing() called with: s = $s")
        if(!isBound) return
        Log.v(tag, "Sending message to service: $s")
        val msg = Message.obtain(null, Proto.C_PING).apply {
        data = Bundle().apply { putString(Proto.C_FETCH_STATUS.toString(), s) }
            replyTo = replyMessenger
        }
        try {

            messenger.send(msg)
        } catch (_: RemoteException) { /* handle remote death */ }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier,send:()->Unit = {}) {
    Column(modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick = send) {
            Text("Send Ping")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IpcplaygroundTheme {
        Greeting("Android")
    }
}