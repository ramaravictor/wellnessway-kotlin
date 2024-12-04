package com.example.wellnessway.presentation.stepCount

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellnessway.WellnessWayApplication
import com.example.wellnessway.data.local.SensorType
import com.example.wellnessway.data.local.schema.History
import com.example.wellnessway.data.remote.exportToCSV
import com.example.wellnessway.domain.model.StepCountData
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.io.IOException


// Panjang langkah rata-rata dalam meter (sesuaikan dengan rata-rata pengguna)
private val STEP_LENGTH_IN_METERS = 0.762 // 76.2 cm (rata-rata)

// Kalori terbakar per langkah (rata-rata, bisa berbeda tergantung berat badan)
private val CALORIES_PER_STEP = 0.05

class StepCountViewModel(
    application: Application
) : AndroidViewModel(application), SensorEventListener {

    private val _state = MutableStateFlow(StepCountState())
    val state: StateFlow<StepCountState> = _state
    private val realm = WellnessWayApplication.realm
    private val stepCounterList = mutableListOf<StepCountData>()
    private var debugSessionCount = 1  // Track debugging sessions for unique titles

    private val context = application
    private val sensorManager: SensorManager =
        getSystemService(context, SensorManager::class.java) as SensorManager

    private var startTimeMillis: Long = 0L // Start time for stopwatch
    private var timerJob: Job? = null // Job for handling elapsed time updates

    init {
        val stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    fun startDebugging() {
        val defaultTitle = "History Data $debugSessionCount"
        startTimeMillis = System.currentTimeMillis() // Capture the start time
        _state.value = _state.value.copy(
            title = defaultTitle,
            isDebugging = true,
            isDebuggingAttempted = false,
            elapsedTime = 0L,
            formattedElapsedTime = "00:00:00",
            distanceInMeters = 0.0, // Reset jarak
            caloriesBurned = 0.0   // Reset kalori
        )

        // Start a coroutine to update the elapsed time in real-time
        timerJob = viewModelScope.launch {
            while (_state.value.isDebugging) {
                delay(1000) // Update every 1 second
                val elapsed = System.currentTimeMillis() - startTimeMillis
                val formattedTime = formatElapsedTime(elapsed)
                _state.value = _state.value.copy(
                    elapsedTime = elapsed,
                    formattedElapsedTime = formattedTime
                )
            }
        }
    }

    fun stopDebugging() {
        _state.value = _state.value.copy(isDebugging = false)
        timerJob?.cancel() // Stop updating the timer when debugging ends
        timerJob = null
        exportDataToCSV()
        debugSessionCount++ // Increment session count for the next debugging session
        clearData() // Clear sensor data after stopping
    }

    private fun exportDataToCSV() {
        val currentTime  = System.currentTimeMillis()
        val history = History().apply {
            timestamp = currentTime
            title = _state.value.title
        }

        SensorType.entries.forEach { sensorType ->
            val dataList = when (sensorType) {

                SensorType.STEP_COUNTER -> stepCounterList
            }
            val fileName = "${_state.value.title}_${sensorType.type}_${currentTime }.csv"
            writeCsv(fileName, dataList)

            val csvFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
            val filePath = csvFile.absolutePath

            when (sensorType) {
                SensorType.STEP_COUNTER -> history.stepCounterPath = filePath
            }
        }

        viewModelScope.launch {
            realm.write {
                copyToRealm(history, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    // Clear the collected sensor data
    private fun clearData() {
        stepCounterList.clear()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                val steps = event.values[0]
                if (_state.value.isDebugging) {
                    val currentStep = steps - _state.value.totalSteps

                    // Hitung jarak dan kalori
                    val distance = currentStep * STEP_LENGTH_IN_METERS
                    val calories = currentStep * CALORIES_PER_STEP

                    // Tambahkan data ke daftar
                    stepCounterList.add(
                        StepCountData(
                            steps = currentStep,
                            distanceInMeters = distance,
                            caloriesBurned = calories,
                            timestamp = System.currentTimeMillis()
                        )
                    )

                    // Perbarui state dengan nilai jarak dan kalori
                    _state.value = _state.value.copy(
                        currentStep = currentStep,
                        distanceInMeters = distance,
                        caloriesBurned = calories
                    )
                } else {
                    _state.value = _state.value.copy(totalSteps = steps)
                }
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    private fun writeCsv(fileName: String, dataList: List<exportToCSV>) {
        val csvFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

        try {
            FileWriter(csvFile).use { writer ->
                // Write header row
                if (dataList.isNotEmpty()) {
                    writer.appendLine(dataList.first().getCsvHeaderRow())
                }

                // Write body rows, each should now include the formatted timestamp
                dataList.forEach { item -> writer.appendLine(item.getCsvBodyRow()) }
                writer.flush()
            }
            Toast.makeText(context, "$fileName exported to ${csvFile.absolutePath}", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error exporting $fileName", Toast.LENGTH_LONG).show()
        }
    }


    // Format the elapsed time into "HH:MM:SS" format
    private fun formatElapsedTime(elapsedMillis: Long): String {
        val hours = (elapsedMillis / 3600000) % 24
        val minutes = (elapsedMillis / 60000) % 60
        val seconds = (elapsedMillis / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
