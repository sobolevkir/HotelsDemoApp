package com.sobolevkir.hotels.data.local

import com.sobolevkir.hotels.data.local.dao.HotelsDao
import com.sobolevkir.hotels.data.local.entity.HotelDetailsEntity
import com.sobolevkir.hotels.data.local.entity.HotelEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val hotelsDao: HotelsDao) {

    fun getHotels(): Flow<List<HotelEntity>> = hotelsDao.getHotels()

    suspend fun saveHotels(hotels: List<HotelEntity>) {
        hotelsDao.clearHotels()
        hotelsDao.insertHotels(hotels)
    }

    fun getHotelDetails(id: Long): Flow<HotelDetailsEntity?> = hotelsDao.getHotelDetails(id)

    suspend fun saveHotelDetails(hotelDetails: HotelDetailsEntity) {
        hotelsDao.clearHotelDetailsById(hotelDetails.id)
        hotelsDao.insertHotelDetails(hotelDetails)
    }

}
