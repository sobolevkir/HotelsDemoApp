package com.sobolevkir.hotels.data.remote.model

import com.google.gson.annotations.SerializedName

data class HotelDetailsResponse(
    val id: Long,
    val name: String,
    val address: String?,
    val stars: Double?,
    val distance: Double?,
    val image: String?,
    @SerializedName("suites_availability") val suitesAvailability: String?,
    val lat: Double?,
    val lon: Double?
)