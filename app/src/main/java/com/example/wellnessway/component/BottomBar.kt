package com.example.wellnessway.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.wellnessway.R
import com.example.wellnessway.presentation.heart_rate.HeartRateRoute
import com.example.wellnessway.presentation.HomeRoute
import com.example.wellnessway.presentation.location.LocationRoute
import com.example.wellnessway.presentation.step_count.StepCountRoute

data class BottomBarItem(val title: String, val icon: Painter, val route: Any)

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val bottomNavigationItems = listOf(
        BottomBarItem(
            title = "Home",
            icon = painterResource(id = R.drawable.ic_fas_home),
            route = HomeRoute
        ),
        BottomBarItem(
            title = "Heart Rate",
            icon = painterResource(id = R.drawable.ic_fas_heartbeat),
            route = HeartRateRoute
        ),
        BottomBarItem(
            title = "Step Count",
            icon = painterResource(id = R.drawable.baseline_map_24),
            route = StepCountRoute
        ),
        BottomBarItem(
            title = "Location",
            icon = painterResource(id = R.drawable.baseline_map_24),
            route = LocationRoute
        )
    )

    // Mendapatkan state dari navigasi saat ini
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
            .height(80.dp)
            .background(Color.White),
        containerColor = Color.Transparent // Pastikan container tidak ada warna abu-abu
    ) {
        bottomNavigationItems.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = item.icon as Painter,
                        contentDescription = item.title,
                        modifier = Modifier.size(if (isSelected) 30.dp else 24.dp),
                        tint = if (isSelected) Color.Red else Color.Red
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = if (isSelected) Color.Red else Color.Red
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}
