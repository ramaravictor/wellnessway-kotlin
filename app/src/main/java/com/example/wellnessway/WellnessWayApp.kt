package com.example.wellnessway

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
import com.example.wellnessway.feature.HeartRateScreen
import com.example.wellnessway.feature.HomeScreen
import com.example.wellnessway.feature.LocationScreen
import com.example.wellnessway.feature.SleepMonitoringScreen
import com.example.wellnessway.feature.SleepStartScreen
import com.example.wellnessway.feature.StepCountScreen

@Composable
fun WellnessWayApp() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.value?.destination?.route

    Scaffold(
        bottomBar = {
            // Tampilkan BottomBar kecuali pada halaman "sleep_start"
            if (currentRoute != "sleep_start") {
                BottomBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home", // Halaman awal
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen(navController = navController) }
            composable("heart_rate") { HeartRateScreen() }
            composable("step_count") { StepCountScreen() }
            composable("sleep_monitoring") { SleepMonitoringScreen(navController = navController) }
            composable("sleep_start") { SleepStartScreen(navController = navController) }
            composable("location") { LocationScreen() }
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun WellnessWayAppPreview() {
    WellnessWayApp()
}
