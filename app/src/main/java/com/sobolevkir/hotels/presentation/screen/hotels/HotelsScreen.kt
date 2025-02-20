package com.sobolevkir.hotels.presentation.screen.hotels

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sobolevkir.hotels.R
import com.sobolevkir.hotels.presentation.navigation.Routes
import com.sobolevkir.hotels.presentation.screen.hotels.component.HotelsList
import com.sobolevkir.hotels.presentation.screen.hotels.component.SortDropdown
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HotelsScreen(onNavigateTo: (Routes) -> Unit = {}) {

    val viewModel: HotelsViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.news.collectLatest { news ->
            when (news) {
                is HotelsNews.ShowMessage -> Toast.makeText(
                    context, context.getString(news.messageResId), Toast.LENGTH_SHORT
                ).show()

                is HotelsNews.NavigateTo -> onNavigateTo(news.route)
            }
        }
    }

    HotelsView(
        onEvent = viewModel::onEvent,
        state = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelsView(
    onEvent: (HotelsUiEvent) -> Unit = {},
    state: HotelsUiState = HotelsUiState()
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.title_hotels)) }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            when {
                state.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(
                            modifier = Modifier.padding(top = 16.dp),
                            text = stringResource(R.string.message_loading)
                        )
                    }
                }

                !state.hotels.isNullOrEmpty() -> {
                    SortDropdown(
                        selectedSortType = state.selectedSortType,
                        onSortTypeSelected = { sortType ->
                            onEvent(HotelsUiEvent.SetHotelsSort(sortType))
                        }
                    )
                    HotelsList(state.hotels) { hotel ->
                        onEvent(HotelsUiEvent.OpenHotelDetails(hotel.id))
                    }
                }

                state.errorMessageResId != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = TextAlign.Center,
                            text = stringResource(state.errorMessageResId),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { onEvent(HotelsUiEvent.Refresh) }) {
                            Text(stringResource(R.string.btn_refresh))
                        }
                    }
                }
            }
        }
    }

}

