package com.android.learning.ipcplayground.ui.screens

import android.R.attr.name
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.learning.ipcplayground.ui.theme.IpcplaygroundTheme

@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier,goToBind:() -> Unit = {},goToForeground:() -> Unit = {},
               goToMessenger:() -> Unit = {}){
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Button(onClick =  goToBind ) {
            Text("Go to Bind Service Screen" )
        }
        Button(onClick =  goToForeground ) {
            Text("Go to Foreground Service Screen" )
        }
        Button(onClick =  goToMessenger ) {
            Text("Go to Messenger Service Screen" )
        }
    }
}

