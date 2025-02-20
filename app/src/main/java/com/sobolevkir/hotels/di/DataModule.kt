package com.sobolevkir.hotels.di

import android.content.Context
import androidx.room.Room
import com.sobolevkir.hotels.data.HotelDetailsRepositoryImpl
import com.sobolevkir.hotels.data.HotelsRepositoryImpl
import com.sobolevkir.hotels.data.local.LocalDataSource
import com.sobolevkir.hotels.data.local.dao.HotelsDao
import com.sobolevkir.hotels.data.local.database.AppDatabase
import com.sobolevkir.hotels.data.mapper.HotelDetailsMapper
import com.sobolevkir.hotels.data.mapper.HotelEntityMapper
import com.sobolevkir.hotels.data.mapper.HotelsMapper
import com.sobolevkir.hotels.data.remote.RemoteDataSource
import com.sobolevkir.hotels.data.remote.api.HotelsApiService
import com.sobolevkir.hotels.domain.api.HotelDetailsRepository
import com.sobolevkir.hotels.domain.api.HotelsRepository
import com.sobolevkir.hotels.domain.model.HotelDetails
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
    }

    @Provides
    fun provideHotelsDao(database: AppDatabase): HotelsDao {
        return database.hotelsDao()
    }

    @Provides
    fun provideHotelEntityMapper(): HotelEntityMapper {
        return HotelEntityMapper()
    }

    @Provides
    fun provideHotelsMapper(): HotelsMapper {
        return HotelsMapper()
    }

    @Provides
    fun provideHotelDetailsMapper(): HotelDetailsMapper {
        return HotelDetailsMapper()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(hotelsDao: HotelsDao): LocalDataSource {
        return LocalDataSource(hotelsDao)
    }

    @Provides
    fun provideRemoteDataSource(apiService: HotelsApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    fun provideHotelsRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        hotelsMapper: HotelsMapper,
        hotelEntityMapper: HotelEntityMapper
    ): HotelsRepository {
        return HotelsRepositoryImpl(
            remoteDataSource,
            localDataSource,
            hotelsMapper,
            hotelEntityMapper
        )
    }

    @Provides
    fun provideHotelDetailsRepository(
        remoteDataSource: RemoteDataSource,
        //localDataSource: LocalDataSource,
        hotelDetailsMapper: HotelDetailsMapper,
        //hotelEntityMapper: HotelEntityMapper
    ): HotelDetailsRepository {
        return HotelDetailsRepositoryImpl(
            remoteDataSource,
            //localDataSource,
            hotelDetailsMapper,
            //hotelEntityMapper
        )
    }


}
