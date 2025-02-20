package com.sobolevkir.hotels.domain.model

data class Hotel(
    val id: Long,
    val name: String,
    val address: String,
    val stars: Int,
    val distance: Double,
    val availableSuites: Int
)