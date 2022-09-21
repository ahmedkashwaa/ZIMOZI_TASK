package com.example.zimozitask.data.databaase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notificationsTable")
data class DatabaseTable(
    @PrimaryKey (autoGenerate = true)
    val id:Int,
    val time:String,
    val date:String,
    val battery:Float

)
