package com.example.zimozitask.presentation.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.zimozitask.utils.NotificationWorker
import com.example.zimozitask.data.databaase.NotificationsDatabase
import com.example.zimozitask.data.repository.NotificationsRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class MainActivityViewModel (app: Application) : AndroidViewModel(app) {
    private val database = NotificationsDatabase.getInstance(app)
    private val repository = NotificationsRepository(database)
    val notifications = repository.items.cachedIn(viewModelScope)

     fun insert(battery:Float){
        viewModelScope.launch {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("d MMM, yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("h:mm a")
            val formatted = current.format(formatter)
            repository.insertNotification(current.format(formatterTime),formatted,battery)
        }

    }


}