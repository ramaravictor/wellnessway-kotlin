package com.example.wellnessway.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination

data class BottomBarItem(val title: String, val icon: ImageVector, val route: String)

@Composable
fun BottomBar(navController: NavController, modifier: Modifier = Modifier) {
    val bottomNavigationItems = listOf(
        BottomBarItem(
            title = "Heart Rate",
            icon = Icons.Default.Favorite,
            route = "heart_rate"
        ),
        BottomBarItem(
            title = "Step Count",
            icon = Icons.Default.Person,
            route = "step_count"
        ),
        BottomBarItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = "home"
        ),
        BottomBarItem(
            title = "Sleep",
            icon = Icons.Default.Notifications,
            route = "sleep_monitoring"
        ),
        BottomBarItem(
            title = "Location",
            icon = Icons.Default.LocationOn,
            route = "location"
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
                    if (isSelected) {

                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color.Red, // Ikon berwarna putih
                                modifier = Modifier.size(50.dp)
                            )

                    } else {
                        // Ikon yang tidak terpilih lebih kecil dan lebih lembut warnanya
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = Color(0xFFFFAFAF), // Warna lembut untuk ikon tidak dipilih
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                label = null, // Tidak menampilkan label
                alwaysShowLabel = false
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
