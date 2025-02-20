package com.sobolevkir.hotels.presentation.screen.hotels.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sobolevkir.hotels.R
import com.sobolevkir.hotels.domain.model.Hotel

@Composable
fun HotelsList(hotels: List<Hotel>, onHotelClick: (Hotel) -> Unit) {
    LazyColumn {
        items(hotels) { hotel ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .animateItem()
                    .clickable { onHotelClick(hotel) }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = hotel.name, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = stringResource(R.string.hotel_distance, hotel.distance))
                    Text(
                        text = stringResource(
                            R.string.hotel_available_suites, hotel.availableSuites
                        )
                    )
                }
            }

        }
    }
}