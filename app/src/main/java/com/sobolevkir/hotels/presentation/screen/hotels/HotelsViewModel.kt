package com.sobolevkir.hotels.presentation.screen.hotels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobolevkir.hotels.domain.usecase.GetHotelsUseCase
import com.sobolevkir.hotels.presentation.navigation.Routes
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType.AVAILABLE_SUITES
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType.DEFAULT
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType.DISTANCE
import com.sobolevkir.hotels.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsViewModel @Inject constructor(
    private val getHotelsUseCase: GetHotelsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HotelsUiState())
    val uiState: StateFlow<HotelsUiState> = _uiState
    private val _news = MutableSharedFlow<HotelsNews>()
    val news = _news.asSharedFlow()

    init {
        loadData()
    }

    fun onEvent(event: HotelsUiEvent) {
        when (event) {
            is HotelsUiEvent.OpenHotelDetails -> viewModelScope.launch {
                _news.emit(HotelsNews.NavigateTo(Routes.HotelDetails(id = event.id)))
            }

            is HotelsUiEvent.SetHotelsSort -> applySort(event.sortType)
            is HotelsUiEvent.Refresh -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            getHotelsUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        val hotels = result.data
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                hotels = hotels,
                                originalHotels = hotels,
                                errorMessageResId = null
                            )
                        }
                    }

                    is Resource.Error -> {
                        val errorMessage = result.messageResId
                        showMessage(errorMessage)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                hotels = null,
                                originalHotels = null,
                                errorMessageResId = errorMessage
                            )
                        }
                    }

                    is Resource.Loading -> _uiState.update {
                        it.copy(
                            isLoading = true,
                            hotels = null,
                            originalHotels = null,
                            errorMessageResId = null
                        )
                    }
                }
            }
        }
    }

    private fun applySort(sortType: HotelsSortType) {
        val sortedHotels = when (sortType) {
            DISTANCE -> _uiState.value.hotels?.sortedBy { it.distance }
            AVAILABLE_SUITES -> _uiState.value.hotels?.sortedByDescending { it.availableSuites }
            DEFAULT -> _uiState.value.originalHotels
        }
        _uiState.update { it.copy(selectedSortType = sortType, hotels = sortedHotels) }
    }

    private fun showMessage(@StringRes messageResId: Int) {
        viewModelScope.launch {
            _news.emit(HotelsNews.ShowMessage(messageResId))
        }
    }

}