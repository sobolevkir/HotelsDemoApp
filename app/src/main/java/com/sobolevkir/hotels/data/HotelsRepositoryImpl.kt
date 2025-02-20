package com.sobolevkir.hotels.data

import com.sobolevkir.hotels.data.local.LocalDataSource
import com.sobolevkir.hotels.data.mapper.HotelEntityMapper
import com.sobolevkir.hotels.data.mapper.HotelsMapper
import com.sobolevkir.hotels.data.remote.RemoteDataSource
import com.sobolevkir.hotels.domain.api.HotelsRepository
import com.sobolevkir.hotels.domain.model.Hotel
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HotelsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val hotelsMapper: HotelsMapper,
    private val hotelEntityMapper: HotelEntityMapper
) : HotelsRepository {

    override fun getHotels(): Flow<Resource<List<Hotel>>> = flow {

        emit(Resource.Loading)

        val cachedHotels = localDataSource.getHotels()
            .map { hotelEntityMapper.mapToDomain(it ) }
            .firstOrNull()

        if (!cachedHotels.isNullOrEmpty()) {
            emit(Resource.Success(cachedHotels))
        }

        when (val response = remoteDataSource.fetchHotels()) {
            is Resource.Success -> {
                val hotels = hotelsMapper.map(response.data)
                localDataSource.saveHotels(hotelEntityMapper.mapToEntity(hotels))
                emit(Resource.Success(hotels))
            }
            is Resource.Error -> {
                if (cachedHotels.isNullOrEmpty()) {
                    emit(Resource.Error(response.messageResId))
                }
            }
            is Resource.Loading -> Unit
        }

    }.flowOn(Dispatchers.IO)

}