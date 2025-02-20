package com.sobolevkir.hotels.data.mapper

import com.sobolevkir.hotels.data.local.entity.HotelEntity
import com.sobolevkir.hotels.domain.model.Hotel
import javax.inject.Inject

class HotelEntityMapper @Inject constructor() {
    fun mapToDomain(entityHotels: List<HotelEntity>): List<Hotel> {
        return entityHotels.map {
            Hotel(
                id = it.id,
                name = it.name,
                address = it.address,
                stars = it.stars,
                distance = it.distance,
                availableSuites = it.availableSuites
            )
        }
    }

    fun mapToEntity(domainHotels: List<Hotel>): List<HotelEntity> {
        return domainHotels.map {
            HotelEntity(
                id = it.id,
                name = it.name,
                address = it.address,
                stars = it.stars,
                distance = it.distance,
                availableSuites = it.availableSuites
            )
        }
    }
}