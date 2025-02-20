package com.sobolevkir.hotels.domain.usecase

import com.sobolevkir.hotels.domain.api.HotelDetailsRepository
import com.sobolevkir.hotels.domain.model.HotelDetails
import com.sobolevkir.hotels.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHotelDetailsUseCase @Inject constructor(private val repository: HotelDetailsRepository) {

    operator fun invoke(id: Long): Flow<Resource<HotelDetails>> = repository.getHotelDetails(id)

}