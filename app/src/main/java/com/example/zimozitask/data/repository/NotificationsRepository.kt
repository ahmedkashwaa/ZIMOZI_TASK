package com.example.zimozitask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.zimozitask.data.databaase.DatabaseTable
import com.example.zimozitask.data.databaase.NotificationsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NotificationsRepository(private val database: NotificationsDatabase) {

    val items = Pager(PagingConfig(pageSize = 20, maxSize = 200)){
        database.dao.getAllPages()
    }.flow

    suspend fun insertNotification(time :String,date :String,battery:Float) {
        withContext(Dispatchers.IO) {
            database.dao.insert(DatabaseTable(0,time,date,battery))
        }
    }


}