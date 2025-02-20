package com.sobolevkir.hotels.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.sobolevkir.hotels.presentation.navigation.AppNavigation
import com.sobolevkir.hotels.presentation.theme.HotelsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            HotelsTheme {
                AppNavigation(navHostController = rememberNavController())
            }
        }
    }

}