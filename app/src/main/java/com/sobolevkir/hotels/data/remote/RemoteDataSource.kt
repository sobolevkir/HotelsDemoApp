package com.sobolevkir.hotels.data.remote

import com.sobolevkir.hotels.R
import com.sobolevkir.hotels.data.remote.api.HotelsApiService
import com.sobolevkir.hotels.data.remote.model.HotelDetailsResponse
import com.sobolevkir.hotels.data.remote.model.HotelResponse
import com.sobolevkir.hotels.util.Resource
import java.io.IOException
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: HotelsApiService) {

    suspend fun fetchHotels(): Resource<List<HotelResponse>> {
        return try {
            val response = apiService.getHotelsList()
            if (response.isNotEmpty()) {
                Resource.Success(response)
            } else {
                Resource.Error(R.string.message_empty_data)
            }
        } catch (e: IOException) {
            Resource.Error(R.string.message_connection_error)
        } catch (e: Exception) {
            Resource.Error(R.string.message_unknown_error)
        }
    }

    suspend fun fetchHotelDetails(id: Long): Resource<HotelDetailsResponse> {
        return try {
            val response = apiService.getHotelDetails(id)
            if (response.name.isNotEmpty()) {
                Resource.Success(response)
            } else {
                Resource.Error(R.string.message_empty_data)
            }
        } catch (e: IOException) {
            Resource.Error(R.string.message_connection_error)
        } catch (e: Exception) {
            Resource.Error(R.string.message_unknown_error)
        }
    }
}