package com.example.hilt.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hilt.model.ActivityResponse

@Database(entities = [ActivityResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun roomDao(): RoomDao
}
