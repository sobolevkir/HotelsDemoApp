package com.sobolevkir.hotels.data

import com.sobolevkir.hotels.data.local.LocalDataSource
import com.sobolevkir.hotels.data.mapper.HotelDetailsEntityMapper
import com.sobolevkir.hotels.data.mapper.HotelDetailsMapper
import com.sobolevkir.hotels.data.remote.RemoteDataSource
import com.sobolevkir.hotels.domain.api.HotelDetailsRepository
import com.sobolevkir.hotels.domain.model.HotelDetails
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HotelDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val hotelDetailsMapper: HotelDetailsMapper,
    private val hotelDetailsEntityMapper: HotelDetailsEntityMapper
) : HotelDetailsRepository {

    override fun getHotelDetails(id: Long): Flow<Resource<HotelDetails>> = flow {

        emit(Resource.Loading)

        val cachedHotelDetails = localDataSource.getHotelDetails(id)
            .map { it?.let { hotelDetailsEntityMapper.mapToDomain(it) } }
            .firstOrNull()

        if (cachedHotelDetails != null) {
            emit(Resource.Success(cachedHotelDetails))
        }

        when (val response = remoteDataSource.fetchHotelDetails(id)) {
            is Resource.Success -> {
                val hotelDetails = hotelDetailsMapper.map(response.data)
                localDataSource.saveHotelDetails(hotelDetailsEntityMapper.mapToEntity(hotelDetails))
                emit(Resource.Success(hotelDetails))
            }

            is Resource.Error -> {
                if (cachedHotelDetails == null) {
                    emit(Resource.Error(response.messageResId))
                }
            }

            is Resource.Loading -> Unit
        }

    }.flowOn(Dispatchers.IO)

}