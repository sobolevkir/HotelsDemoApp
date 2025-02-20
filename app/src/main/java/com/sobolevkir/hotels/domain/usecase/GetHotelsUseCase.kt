package com.sobolevkir.hotels.domain.usecase

import com.sobolevkir.hotels.domain.api.HotelsRepository
import com.sobolevkir.hotels.domain.model.Hotel
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHotelsUseCase @Inject constructor(private val repository: HotelsRepository) {

    operator fun invoke(): Flow<Resource<List<Hotel>>> = repository.getHotels()

}