package com.android.learning.ipcplayground.ui.screens

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.android.learning.ipcplayground.services.ForegroundService


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForegroundScreen() {
    val context = LocalContext.current
    val activity = context.findActivity()
    val intent = Intent(activity, ForegroundService::class.java)

    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text("Foreground Service Screen")
        Button(onClick = {
            activity.startForegroundService(intent)

        }) {
            Text("Launch Foreground Service")
        }

        Button(onClick = {
            activity.stopService(intent)

        }) {
            Text("Stop Foreground Service")
        }
    }
}