package com.sobolevkir.hotels.presentation.screen.hotels

import androidx.annotation.StringRes
import com.sobolevkir.hotels.domain.model.Hotel

data class HotelsUiState(

    val isLoading: Boolean = false,
    val hotels: List<Hotel>? = emptyList(),
    val originalHotels: List<Hotel>? = emptyList(),
    val selectedSortType: HotelsSortType = HotelsSortType.DEFAULT,
    @StringRes val errorMessageResId: Int? = null

)