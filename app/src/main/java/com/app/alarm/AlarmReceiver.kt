package com.app.alarm


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class AlarmReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        Log.d("abdo", "serciceCreate")
        val workRequest = OneTimeWorkRequest.Builder(MusicServiceWorker::class.java).build()
        WorkManager.getInstance(context).enqueue(workRequest)


    }
}
