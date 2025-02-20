package com.sobolevkir.hotels.presentation.navigation

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sobolevkir.hotels.R
import com.sobolevkir.hotels.presentation.screen.hotel_details.HotelDetailsFragment
import com.sobolevkir.hotels.presentation.screen.hotels.HotelsScreen
import com.sobolevkir.hotels.presentation.theme.HotelsTheme

@Composable
fun AppNavigation(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Routes.Hotels,
    ) {
        val navigateAction: (Routes) -> Unit = { route ->
            when (route) {
                is Routes.GoBack -> navHostController.navigateUp()
                else -> navHostController.navigate(route)
            }
        }

        composable<Routes.Hotels> {
            HotelsScreen(onNavigateTo = navigateAction)
        }

        composable<Routes.HotelDetails> { backStackEntry ->
            val route: Routes.HotelDetails = backStackEntry.toRoute()
            val hotelId: Long = route.id
            Scaffold { paddingValues ->
                AndroidView(
                    factory = { context ->
                        val container =
                            FrameLayout(context).apply { id = R.id.fragment_container }
                        val fragment = HotelDetailsFragment().apply {
                            arguments = bundleOf("id" to hotelId)
                        }
                        (context as AppCompatActivity).supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit()
                        container
                    },
                    modifier = Modifier.fillMaxSize().padding(paddingValues)
                )

            }
        }
    }

}