package com.example.zimozitask.data.databaase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1,entities = [DatabaseTable::class])
abstract class NotificationsDatabase : RoomDatabase() {
    abstract val dao: DatabaseDao

    companion object {
        @Volatile
        private lateinit var instance: NotificationsDatabase

        fun getInstance(context: Context): NotificationsDatabase {
            synchronized(NotificationsDatabase::class.java) {
                if(!Companion::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotificationsDatabase::class.java,
                        "notifications.db")
                        .build()
                }
            }
            return instance
        }
    }


}