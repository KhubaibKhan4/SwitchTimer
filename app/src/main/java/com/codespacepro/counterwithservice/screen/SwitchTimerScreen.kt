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
import androidx.compose.foundation.layout.height
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

@Composable
fun SwitchTimer() {
    var counter by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val intentService = remember {
        Intent(context, TimeSwitchService::class.java)
    }
    val broadcast = remember {
        object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                counter = p1?.getIntExtra("Switch_Timer", 0) ?: 0
            }

        }
    }

    LaunchedEffect(key1 = true) {
        val intentFilters = IntentFilter("Switch")
        context.registerReceiver(broadcast, intentFilters)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$counter")
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { context.startService(intentService) }) {
                Text(text = "Start Switch")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = { context.startService(intentService) }) {
                Text(text = "Stop Switch")
            }
        }
    }
}