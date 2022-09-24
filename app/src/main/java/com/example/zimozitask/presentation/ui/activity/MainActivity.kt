package com.example.zimozitask.presentation.ui.activity

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.zimozitask.databinding.ActivityMainBinding
import com.example.zimozitask.presentation.ui.adapter.RvPagedListAdapter
import com.example.zimozitask.presentation.viewmodel.MainActivityViewModel
import com.example.zimozitask.utils.NotificationWorker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    val workManager = WorkManager.getInstance(this)
        lateinit var binding:ActivityMainBinding
           var adapter: RvPagedListAdapter? =null

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
    var timeAt: LocalDateTime = LocalDate.now().atTime(8, 0)
    var timeNow = LocalDateTime.now()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = RvPagedListAdapter()

        if (Duration.between(timeNow,timeAt).isNegative){
            timeAt = LocalDate.now().plusDays(1).atTime(8,0)
        }


        lifecycleScope.launchWhenCreated {
            viewModel.notifications.collectLatest {
                adapter?.submitData(it)
            }
        }


        binding.rv.adapter = adapter

       val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(2,TimeUnit.HOURS)
           .setInitialDelay(Duration.between(timeNow,timeAt))
           .build()
        // Avoiding duplicating PeriodicWorkRequest from WorkManager , i used enqueueUniquePeriodicWork instead of just enqueue
        workManager.enqueueUniquePeriodicWork("Send Notification",  ExistingPeriodicWorkPolicy.REPLACE,workRequest)

    }
}