package com.sobolevkir.hotels.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobolevkir.hotels.data.local.entity.HotelDetailsEntity
import com.sobolevkir.hotels.data.local.entity.HotelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelsDao {

    @Query("SELECT * FROM hotels ORDER BY orderIndex ASC")
    fun getHotels(): Flow<List<HotelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotels(hotels: List<HotelEntity>)

    @Query("DELETE FROM hotels")
    suspend fun clearHotels()

    @Query("SELECT * FROM hotel_details WHERE id = :id LIMIT 1")
    fun getHotelDetails(id: Long): Flow<HotelDetailsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotelDetails(hotelDetails: HotelDetailsEntity)

    @Query("DELETE FROM hotel_details WHERE id = :id")
    suspend fun clearHotelDetailsById(id: Long)

}