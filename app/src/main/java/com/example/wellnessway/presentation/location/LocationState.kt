package com.example.wellnessway.presentation.location

import com.example.wellnessway.domain.model.LocationItem


data class LocationState(
    val isLoading: Boolean = false,
    val error: String = "",
    val list: List<LocationItem> = emptyList()
)