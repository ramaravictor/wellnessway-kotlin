package com.example.sensorapp.data.model

import com.example.sensorapp.data.remote.exportToCSV
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class AccelerometerData(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f,
    val timestamp: Long? = 0
): exportToCSV {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun getCsvBodyRow(): String {
        val formattedTimestamp = timestamp?.let { dateFormat.format(Date(it)) } ?: ""
        return "$x,$y,$z,$formattedTimestamp"
    }

    override fun getCsvHeaderRow(): String {
        return "x,y,z,timestamp"
    }
}
