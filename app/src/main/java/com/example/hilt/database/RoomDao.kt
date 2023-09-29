package com.example.hilt.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hilt.model.ActivityResponse

@Dao
interface RoomDao {
    @Insert
    fun insert(activityResponse: ActivityResponse)

    @Query("SELECT * FROM activity")
    fun getAllUsers(): List<ActivityResponse>

    @Delete
    fun delete(activityResponse: ActivityResponse)

    @Update
    fun update(activityResponse: ActivityResponse)

    @Query("DELETE FROM activity")
    fun clearAllData()
}