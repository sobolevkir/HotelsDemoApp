package com.sobolevkir.hotels.presentation.screen.hotel_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        loadData()
    }

    private val hotelId: Long = checkNotNull(savedStateHandle.get<Long>("id"))

    private val _uiState = MutableStateFlow(HotelDetailsUiState())
    val uiState: StateFlow<HotelDetailsUiState> = _uiState

    fun onEvent(event: HotelDetailsUiEvent) {
        when (event) {
            is HotelDetailsUiEvent.Refresh -> loadData()
            is HotelDetailsUiEvent.BackButtonPressed -> {}/*viewModelScope.launch {
                _news.emit(HotelsNews.NavigateTo(Routes.HotelDetails(id = event.id)))
            }*/
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            getHotelDetailsUseCase(hotelId).collect { result ->
                Log.d("123", result.toString())
                when (result) {
                    is Resource.Success -> {
                        val hotelDetails = result.data
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