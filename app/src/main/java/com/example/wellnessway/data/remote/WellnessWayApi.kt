package com.example.wellnessway.data.remote

import com.example.wellnessway.domain.model.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WellnessWayApi {
    @GET("location")
    suspend fun getLocations(
        @Query("latitude") latitude: Double? = null,
        @Query("longitude") longitude: Double? = null
    ): LocationResponse
}