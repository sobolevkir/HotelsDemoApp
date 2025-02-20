package com.sobolevkir.hotels.data.mapper

import com.sobolevkir.hotels.data.remote.model.HotelResponse
import com.sobolevkir.hotels.domain.model.Hotel
import javax.inject.Inject

class HotelsMapper @Inject constructor() {
    fun map(response: List<HotelResponse>): List<Hotel> {

        // Можно в будущем перенести обработку отсутствующих значений в UI

        return response.map {
            Hotel(
                id = it.id,
                name = it.name,
                address = it.address ?: "",
                stars = it.stars?.toInt() ?: 0,
                distance = it.distance ?: 0.0,
                availableSuites = it.suitesAvailability?.split(":")?.size ?: 0
            )
        }
    }
}