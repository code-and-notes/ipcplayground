package com.android.learning.ipcplayground.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.learning.ipcplayground.services.EchoService
import com.android.learning.ipcplayground.ui.screens.Screens
import com.android.learning.ipcplayground.ui.theme.IpcplaygroundTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.android.learning.ipcplayground.ui.screens.BindScreen
import com.android.learning.ipcplayground.ui.screens.ForegroundScreen
import com.android.learning.ipcplayground.ui.screens.MainScreen
import com.android.learning.ipcplayground.ui.screens.MessengerScreen


class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            IpcplaygroundTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.safeDrawing)
                        .padding(20.dp)
                ) { innerPadding ->
                    App(modifier = Modifier.padding(innerPadding),
                    )

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(
    modifier: Modifier = Modifier,
) {
    var screen: Screens by remember { mutableStateOf(Screens.MAIN) }

    when (screen) {
        Screens.MAIN -> MainScreen("Hey Android", modifier = modifier, goToBind = {screen = Screens.BIND},goToForeground = {screen = Screens.FOREGROUND },
            goToMessenger = {screen = Screens.MESSENGER})
        Screens.BIND -> BindScreen()
        Screens.FOREGROUND -> ForegroundScreen()
        Screens.MESSENGER -> MessengerScreen()
    }
    val context = LocalContext.current
    val intent = Intent(context, EchoService::class.java)
    context.startService(intent)
    context.stopService(intent)
    BackHandler() {
         screen = Screens.MAIN
    }

}


