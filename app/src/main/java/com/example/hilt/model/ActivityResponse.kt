package com.example.hilt.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity")
data class ActivityResponse(
    @PrimaryKey(autoGenerate = true)
    val primaryKey: Int = 1,
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
)