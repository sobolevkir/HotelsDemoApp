package com.sobolevkir.hotels.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sobolevkir.hotels.data.local.entity.HotelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelsDao {

    @Query("SELECT * FROM hotels")
    fun getHotels(): Flow<List<HotelEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotels(hotels: List<HotelEntity>)

    @Query("DELETE FROM hotels")
    suspend fun clearHotels()

}