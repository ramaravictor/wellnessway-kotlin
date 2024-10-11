package com.example.wellnessway.domain.repository

import com.example.wellnessway.domain.model.LocationResponse


interface WellnessWayRepository {
    suspend fun getLocation(
        latitude: Double? = null,
        longitude: Double? = null
    ): LocationResponse
}