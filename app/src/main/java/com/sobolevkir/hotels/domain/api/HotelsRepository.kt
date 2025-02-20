package com.sobolevkir.hotels.domain.api

import com.sobolevkir.hotels.domain.model.Hotel
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.flow.Flow

interface HotelsRepository {

    fun getHotels(): Flow<Resource<List<Hotel>>>

}