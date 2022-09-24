package com.example.zimozitask.utils

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.zimozitask.presentation.viewmodel.MainActivityViewModel
import java.util.*

class NotificationWorker(val context: Context,val params: WorkerParameters) :Worker(context,params) {
    // calculating battery percentage
    val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
        context.registerReceiver(null, ifilter)
    }
    val batteryPct: Float? = batteryStatus?.let { intent ->
        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        level * 100 / scale.toFloat()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)
        if (timeOfDay in 8..12){
            val viewModel =  MainActivityViewModel(applicationContext as Application)
            // insert notification in Room and create notification
            viewModel.insert(batteryPct!!)
            return Result.success()
        } else{
            return Result.success()
        }
    }
}