package com.codespacepro.counterwithservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CounterService : Service() {

    var counter = 0
    var isRunning = false
    var job: Job? = null

    override fun onCreate() {
        super.onCreate()
        Log.d("TAG", "onCreate: Service Running")
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isRunning = true
        job = CoroutineScope(Dispatchers.Default).launch {
            while (isRunning) {
                delay(1000)
                counter++
                val intent = Intent("counter_screen").apply {
                    putExtra("counter", counter)
                }
                sendBroadcast(intent)
            }

        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        isRunning = false
    }
}