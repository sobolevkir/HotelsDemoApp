package com.sobolevkir.hotels.presentation.screen.hotel_details

sealed interface HotelDetailsUiEvent {

    data object Refresh : HotelDetailsUiEvent
    data object BackButtonPressed : HotelDetailsUiEvent

}