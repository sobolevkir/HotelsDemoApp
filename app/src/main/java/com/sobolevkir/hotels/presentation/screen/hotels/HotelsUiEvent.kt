package com.sobolevkir.hotels.presentation.screen.hotels

sealed interface HotelsUiEvent {

    data class OpenHotelDetails(val id: Long) : HotelsUiEvent
    data class SetHotelsSort(val sortType: HotelsSortType) : HotelsUiEvent
    data object Refresh : HotelsUiEvent

}