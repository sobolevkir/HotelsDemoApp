package com.sobolevkir.hotels.presentation.screen.hotels.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sobolevkir.hotels.R
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType.AVAILABLE_SUITES
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType.DEFAULT
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsSortType.DISTANCE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDropdown(
    selectedSortType: HotelsSortType,
    onSortTypeSelected: (HotelsSortType) -> Unit
) {
    val sortOptions = listOf(
        DEFAULT to stringResource(R.string.sort_default),
        DISTANCE to stringResource(R.string.sort_distance),
        AVAILABLE_SUITES to stringResource(R.string.sort_available_suites)
    )
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = sortOptions.find { it.first == selectedSortType }?.second ?: "",
            onValueChange = {},
            label = { Text(stringResource(R.string.label_sort)) },
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, true),
            shape = RoundedCornerShape(16.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            sortOptions.forEach { (sortType, label) ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        onSortTypeSelected(sortType)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}