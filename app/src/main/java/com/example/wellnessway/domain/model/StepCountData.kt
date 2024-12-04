package com.example.wellnessway.domain.model


import com.example.wellnessway.data.remote.exportToCSV
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class StepCountData(
    val steps: Float = 0f,
    val distanceInMeters: Double = 0.0, // Ubah ke Double
    val caloriesBurned: Double = 0.0,
    val timestamp: Long? = 0
) : exportToCSV {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun getCsvBodyRow(): String {
        val formattedTimestamp = timestamp?.let { dateFormat.format(Date(it)) } ?: ""
        return "$steps,$caloriesBurned,$distanceInMeters,$formattedTimestamp"
    }

    override fun getCsvHeaderRow(): String {
        return "steps,caloriesBurned,distanceInMeters,timestamp"
    }
}
