package com.sobolevkir.hotels.presentation.screen.hotel_details

import androidx.annotation.StringRes
import com.sobolevkir.hotels.domain.model.Hotel
import com.sobolevkir.hotels.domain.model.HotelDetails

data class HotelDetailsUiState(

    val isLoading: Boolean = false,
    val hotelDetails: HotelDetails? = null,
    @StringRes val errorMessageResId: Int? = null

)