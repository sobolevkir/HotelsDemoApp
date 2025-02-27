package com.sobolevkir.hotels.data.mapper

import com.sobolevkir.hotels.data.local.entity.HotelDetailsEntity
import com.sobolevkir.hotels.domain.model.HotelDetails
import javax.inject.Inject

class HotelDetailsEntityMapper @Inject constructor() {

    fun mapToDomain(entity: HotelDetailsEntity): HotelDetails {
        return HotelDetails(
            id = entity.id,
            name = entity.name,
            address = entity.address,
            stars = entity.stars,
            distance = entity.distance,
            image = entity.image,
            availableSuites = entity.availableSuites,
            lat = entity.lat,
            lon = entity.lon
        )
    }

    fun mapToEntity(domain: HotelDetails): HotelDetailsEntity {
        return HotelDetailsEntity(
            id = domain.id,
            name = domain.name,
            address = domain.address,
            stars = domain.stars,
            distance = domain.distance,
            image = domain.image,
            availableSuites = domain.availableSuites,
            lat = domain.lat,
            lon = domain.lon
        )
    }
}