package com.sobolevkir.hotels.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sobolevkir.hotels.data.local.dao.HotelsDao
import com.sobolevkir.hotels.data.local.entity.HotelEntity

@Database(
    entities = [HotelEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hotelsDao(): HotelsDao

}