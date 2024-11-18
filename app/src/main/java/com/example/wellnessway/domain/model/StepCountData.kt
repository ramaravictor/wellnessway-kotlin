package com.example.sensorapp.data.model

import com.example.sensorapp.data.remote.exportToCSV
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class StepCountData(
    val steps: Float = 0f,
    val timestamp: Long? = 0
) : exportToCSV {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun getCsvBodyRow(): String {
        val formattedTimestamp = timestamp?.let { dateFormat.format(Date(it)) } ?: ""
        return "$steps,$formattedTimestamp"
    }

    override fun getCsvHeaderRow(): String {
        return "steps,timestamp"
    }
}
