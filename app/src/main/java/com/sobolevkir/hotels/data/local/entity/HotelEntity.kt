package com.sobolevkir.hotels.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotels")
data class HotelEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val address: String,
    val stars: Int,
    val distance: Double,
    val availableSuites: Int,
    val orderIndex: Int
)