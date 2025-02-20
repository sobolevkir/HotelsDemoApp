package com.sobolevkir.hotels.data.remote.api

import com.sobolevkir.hotels.data.remote.api.ApiConstants.HOTELS_LIST_ENDPOINT
import com.sobolevkir.hotels.data.remote.model.HotelDetailsResponse
import com.sobolevkir.hotels.data.remote.model.HotelResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HotelsApiService {

    @GET(HOTELS_LIST_ENDPOINT)
    suspend fun getHotelsList(): List<HotelResponse>

    @GET("{id}.json")
    suspend fun getHotelDetails(@Path("id") hotelId: Long): HotelDetailsResponse

}