package com.example.hilt.network

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hilt.model.ActivityResponse
import retrofit2.Response
import retrofit2.http.GET

@Dao
interface ApiServices {
    @GET("activity")
    suspend fun getRepo(): Response<ActivityResponse>

}