package com.example.wellnessway.domain.model

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    val data: List<LocationItem>
)

data class LocationItem(
    val address: String,
    val email: String,
    val id: Int,
    val latitude: String,
    val longitude: String,
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("image_url")
    val imageUrl: String?
)