package com.sobolevkir.hotels.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hotel_details")
data class HotelDetailsEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val address: String,
    val stars: Int,
    val distance: Double,
    val image: String,
    val availableSuites: Int,
    val lat: Double,
    val lon: Double
)
