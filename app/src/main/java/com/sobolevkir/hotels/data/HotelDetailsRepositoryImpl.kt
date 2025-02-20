package com.sobolevkir.hotels.data

import com.sobolevkir.hotels.data.mapper.HotelDetailsMapper
import com.sobolevkir.hotels.data.remote.RemoteDataSource
import com.sobolevkir.hotels.domain.api.HotelDetailsRepository
import com.sobolevkir.hotels.domain.model.HotelDetails
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HotelDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    //private val localDataSource: LocalDataSource,
    private val hotelDetailsMapper: HotelDetailsMapper,
    //private val hotelEntityMapper: HotelEntityMapper
) : HotelDetailsRepository {

    override fun getHotelDetails(id: Long): Flow<Resource<HotelDetails>> = flow {

        emit(Resource.Loading)

        /*val cachedHotels = localDataSource.getHotels()
            .map { hotelEntityMapper.mapToDomain(it ) }
            .firstOrNull()

        if (!cachedHotels.isNullOrEmpty()) {
            emit(Resource.Success(cachedHotels))
        }*/

        when (val response = remoteDataSource.fetchHotelDetails(id)) {
            is Resource.Success -> {
                val hotelDetails = hotelDetailsMapper.map(response.data)
                //localDataSource.saveHotels(hotelEntityMapper.mapToEntity(hotels))
                emit(Resource.Success(hotelDetails))
            }

            is Resource.Error -> {
                emit(Resource.Error(response.messageResId))
                /*if (cachedHotels.isNullOrEmpty()) {
                    emit(Resource.Error(response.messageResId))
                }*/
            }

            is Resource.Loading -> Unit
        }

    }.flowOn(Dispatchers.IO)

}