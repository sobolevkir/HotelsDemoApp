package com.sobolevkir.hotels.di

import com.sobolevkir.hotels.data.remote.api.ApiConstants
import com.sobolevkir.hotels.data.remote.api.HotelsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideHotelsApiService(retrofit: Retrofit): HotelsApiService {
        return retrofit.create(HotelsApiService::class.java)
    }

}