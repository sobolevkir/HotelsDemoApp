package com.sobolevkir.hotels.domain.api

import com.sobolevkir.hotels.domain.model.HotelDetails
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.flow.Flow

interface HotelDetailsRepository {

    fun getHotelDetails(id: Long): Flow<Resource<HotelDetails>>

}