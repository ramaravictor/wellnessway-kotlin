package com.example.wellnessway.data.repository

import com.example.wellnessway.data.remote.WellnessWayApi
import com.example.wellnessway.domain.model.LocationResponse
import com.example.wellnessway.domain.repository.WellnessWayRepository
import javax.inject.Inject

class WellnessWayRepositoryImpl @Inject constructor(
    private val wellnessWayApi: WellnessWayApi
) : WellnessWayRepository {
    override suspend fun getLocation(latitude: Double?, longitude: Double?): LocationResponse {
        return wellnessWayApi.getLocations(latitude, longitude)
    }
}