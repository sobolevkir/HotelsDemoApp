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
        return fetchData(
            fetch = { apiService.getHotelsList() },
            emptyCheck = { it.isEmpty() }
        )
    }

    suspend fun fetchHotelDetails(id: Long): Resource<HotelDetailsResponse> {
        return fetchData(
            fetch = { apiService.getHotelDetails(id) },
            emptyCheck = { it.name.isEmpty() }
        )
    }

    private suspend fun <T> fetchData(
        fetch: suspend () -> T,
        emptyCheck: (T) -> Boolean
    ): Resource<T> {
        return try {
            val response = fetch()
            if (emptyCheck(response)) {
                Resource.Error(R.string.message_empty_data)
            } else {
                Resource.Success(response)
            }
        } catch (e: IOException) {
            Resource.Error(R.string.message_connection_error)
        } catch (e: Exception) {
            Resource.Error(R.string.message_unknown_error)
        }
    }
}