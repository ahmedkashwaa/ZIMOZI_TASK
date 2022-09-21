package com.example.zimozitask.data.databaase

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface DatabaseDao {

    @Query("SELECT * FROM notificationsTable ORDER BY id DESC")
    fun getAllPages(): PagingSource<Int,DatabaseTable>

    @Insert
    fun insert(notification: DatabaseTable)


}