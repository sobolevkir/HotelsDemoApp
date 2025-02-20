package com.sobolevkir.hotels.presentation.screen.hotel_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobolevkir.hotels.domain.usecase.GetHotelDetailsUseCase
import com.sobolevkir.hotels.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelDetailsViewModel @Inject constructor(
    private val getHotelDetailsUseCase: GetHotelDetailsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HotelDetailsUiState())
    val uiState: StateFlow<HotelDetailsUiState> = _uiState
    private var isDataLoaded = false

    fun loadData(hotelId: Long) {
        if (isDataLoaded) return
        viewModelScope.launch {
            getHotelDetailsUseCase(hotelId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val hotelDetails = result.data
                        isDataLoaded = true
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                hotelDetails = hotelDetails,
                                errorMessageResId = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        val errorMessage = result.messageResId
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                hotelDetails = null,
                                errorMessageResId = errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> _uiState.update {
                        it.copy(
                            isLoading = true,
                            hotelDetails = null,
                            errorMessageResId = null
                        )
                    }
                }
            }
        }
    }

}