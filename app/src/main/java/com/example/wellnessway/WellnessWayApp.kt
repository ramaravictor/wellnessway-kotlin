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
import androidx.navigation.compose.rememberNavController
import com.example.wellnessway.presentation.component.BottomBar
import com.example.wellnessway.data.local.schema.History
import com.example.wellnessway.presentation.HomeRoute
import com.example.wellnessway.presentation.HomeScreen
import com.example.wellnessway.presentation.history.HistoryRoute
import com.example.wellnessway.presentation.history.HistoryScreen
import com.example.wellnessway.presentation.location.LocationRoute
import com.example.wellnessway.presentation.location.LocationScreen
import com.example.wellnessway.presentation.stepCount.StepCountRoute
import com.example.wellnessway.presentation.stepCount.StepCountScreen
import dagger.hilt.android.HiltAndroidApp
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration


@HiltAndroidApp
class WellnessWayApplication : Application(){
    companion object {
        lateinit var realm: Realm
    }
    override fun onCreate() {
        super.onCreate()

        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(History::class)
            )
        )
    }
}

@Composable
fun WellnessWayApp() {
    val navController = rememberNavController()
//    val navBackStackEntry = navController.currentBackStackEntryAsState()

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

//            composable<HeartRateRoute> {
//                HeartRateScreen(
//                    navController = navController
//                )
//            }

            composable<LocationRoute> {
                LocationScreen(
                    navController = navController
                )
            }

            composable<StepCountRoute> {
                StepCountScreen(
                    navController = navController
                )
            }

            composable<HistoryRoute> {
                HistoryScreen(
                    navController = navController
                )
            }

//            composable("step_count") { StepCountScreen() }

        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun WellnessWayAppPreview() {
    WellnessWayApp()
}