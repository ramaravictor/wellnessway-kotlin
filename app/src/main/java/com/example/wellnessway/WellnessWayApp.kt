package com.example.wellnessway

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wellnessway.component.BottomBar
import com.example.wellnessway.presentation.heart_rate.HeartRateRoute
import com.example.wellnessway.presentation.heart_rate.HeartRateScreen
import com.example.wellnessway.presentation.HomeRoute
import com.example.wellnessway.presentation.HomeScreen
import com.example.wellnessway.presentation.location.LocationRoute
import com.example.wellnessway.presentation.location.LocationScreen
import com.example.wellnessway.presentation.step_count.StepCountRoute
import com.example.wellnessway.presentation.step_count.StepCountScreen
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class WellnessWayApplication : Application()

@Composable
fun WellnessWayApp() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            // Tampilkan BottomBar kecuali pada halaman "sleep_start"

                BottomBar(navController = navController)

        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute, // Halaman awal
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    navController = navController
                )
            }

            composable<HeartRateRoute> {
                HeartRateScreen(
                    navController = navController
                )
            }

            composable<StepCountRoute> {
                StepCountScreen(
                    navController = navController
                )
            }

//            composable("step_count") { StepCountScreen() }
            composable<LocationRoute> {
                LocationScreen(
                    navController = navController
                )
            }
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun WellnessWayAppPreview() {
    WellnessWayApp()
}
