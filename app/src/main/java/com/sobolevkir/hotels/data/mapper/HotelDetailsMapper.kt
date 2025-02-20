package com.sobolevkir.hotels.data.mapper

import com.sobolevkir.hotels.data.remote.api.ApiConstants
import com.sobolevkir.hotels.data.remote.model.HotelDetailsResponse
import com.sobolevkir.hotels.domain.model.HotelDetails
import javax.inject.Inject

class HotelDetailsMapper @Inject constructor() {
    fun map(response: HotelDetailsResponse): HotelDetails {

        // Можно в будущем перенести обработку отсутствующих значений в UI

        return HotelDetails(
            id = response.id,
            name = response.name,
            address = response.address ?: "",
            stars = response.stars?.toInt() ?: 0,
            distance = response.distance ?: 0.0,
            availableSuites = response.suitesAvailability?.split(":")?.size ?: 0,
            lat = response.lat ?: 0.0,
            lon = response.lon ?: 0.0,
            image = ApiConstants.BASE_URL + (response.image ?: "")
        )
    }
}