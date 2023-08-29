package com.codespacepro.counterwithservice.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.codespacepro.counterwithservice.CounterService

@Composable
fun CounterScreen() {

    var counter by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val intentService = remember {
        Intent(context, CounterService::class.java)
    }
    val broadCast = remember {
        object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                counter = intent?.getIntExtra("counter", 0) ?: 0
            }

        }
    }
    LaunchedEffect(key1 = true) {
        val intentFilter = IntentFilter("counter_screen")
        context.registerReceiver(broadCast, intentFilter)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$counter")

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { context.startService(intentService) }) {
                Text(text = "Start Service")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { context.stopService(intentService) }) {
                Text(text = "Stop Service")
            }
        }
    }
}