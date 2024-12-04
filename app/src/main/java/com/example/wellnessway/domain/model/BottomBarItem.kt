package com.example.wellnessway.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val title: String,
    val icon: Any, // Bisa ImageVector atau Painter
    val route: Any
)

