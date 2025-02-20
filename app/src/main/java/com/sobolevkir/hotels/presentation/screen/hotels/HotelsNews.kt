package com.sobolevkir.hotels.presentation.screen.hotels

import androidx.annotation.StringRes
import com.sobolevkir.hotels.presentation.navigation.Routes

sealed interface HotelsNews {

    data class NavigateTo(val route: Routes) : HotelsNews
    data class ShowMessage(@StringRes val messageResId: Int) : HotelsNews

}