package com.sobolevkir.hotels.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object GoBack : Routes()
    @Serializable
    data object Hotels : Routes()
    @Serializable
    data class HotelDetails(val id: Long) : Routes()

}