package com.sobolevkir.hotels.domain.model

data class HotelDetails(
    val id: Long,
    val name: String,
    val address: String,
    val stars: Int,
    val distance: Double,
    val image: String,
    val availableSuites: Int,
    val lat: Double,
    val lon: Double
)