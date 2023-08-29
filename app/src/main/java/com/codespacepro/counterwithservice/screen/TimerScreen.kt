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
import com.codespacepro.counterwithservice.service.TimeService

@Composable
fun TimerScreen() {
    var counter by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val intentService = remember {
        Intent(context, TimeService::class.java)
    }

    val broadCast = remember {
        object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                counter = p1?.getIntExtra("timer", 0) ?: 0
            }

        }
    }
    LaunchedEffect(key1 = true) {
        val intentFilers = IntentFilter("timer_service")
        context.registerReceiver(broadCast, intentFilers)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "$counter")
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = { context.startService(intentService) }) {
                Text(text = "Start Timer")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { context.stopService(intentService) }) {
                Text(text = "Stop Timer")
            }
        }
    }
}