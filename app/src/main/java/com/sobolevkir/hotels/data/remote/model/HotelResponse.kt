package com.sobolevkir.hotels.data.remote.model

import com.google.gson.annotations.SerializedName

data class HotelResponse(
    val id: Long,
    val name: String,
    val address: String?,
    val stars: Double?,
    val distance: Double?,
    @SerializedName("suites_availability")
    val suitesAvailability: String?
)