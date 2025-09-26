package com.android.learning.ipcplayground.ui.screens


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.ServiceConnection
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.learning.ipcplayground.services.BindEchoService
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue


@Composable
fun BindScreen() {

    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    var bindingService: BindEchoService? by remember { mutableStateOf(null) }
    var isBound: Boolean by remember { mutableStateOf(false) }
    var number by remember { mutableIntStateOf(1) }
    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: android.content.ComponentName?, service: android.os.IBinder?) {
                Log.w("BindScreen", "onServiceConnected() called with: name = $name, service = $service")
                val binder = service as BindEchoService.LocalEchoBinder
                 bindingService = binder.getService()
            }

            override fun onServiceDisconnected(name: android.content.ComponentName?) {
                bindingService = null
                Log.w("BindScreen", "onServiceDisconnected() called with: name = $name")
            }

        }}

    val tag = "BindScreen"

    SideEffect {
        Log.w(tag, "BindScreen() called" )
    }
    LaunchedEffect(isBound) {
        if (isBound) {
            val intent = Intent(activity, BindEchoService::class.java)
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
            Log.w(tag, "Binding to service" )
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { isBound = true }) {
            SideEffect {
                Log.w(tag, "Bound to service" )
            }
            Text("Bind to BindEchoService" )
        }
        Button(onClick = { context.unbindService(connection)
        isBound = false}) {
            SideEffect {
                Log.w(tag, "UnBound to service" )
            }
            Text("UnBind to BindEchoService" )
        }

        Button(onClick = { number = bindingService?.getRandomNumber ?: -1 }) {
            SideEffect {
                Log.w(tag, "UnBound to service" )
            }
            Text("get random int using service" )
        }
        Text("Random number from service is $number" )
    }

}

fun Context.findActivity() : Activity {
    return when(this){
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> throw IllegalStateException("Context $this is not an Activity")
    }
}