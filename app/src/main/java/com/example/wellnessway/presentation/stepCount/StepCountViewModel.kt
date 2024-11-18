package com.example.wellnessway.presentation.step_count

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
import com.example.wellnessway.domain.model.AccelerometerData
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

class StepCountViewModel(
    application: Application
) : AndroidViewModel(application), SensorEventListener {

    private val _state = MutableStateFlow(StepCountState())
    val state: StateFlow<StepCountState> = _state
    private val realm = WellnessWayApplication.realm
    private val stepCounterList = mutableListOf<StepCountData>()
    private val accelerometerList = mutableListOf<AccelerometerData>()
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

        val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun startDebugging() {
        val defaultTitle = "Debug Data $debugSessionCount"
        startTimeMillis = System.currentTimeMillis() // Capture the start time
        _state.value = _state.value.copy(
            title = defaultTitle,
            isDebugging = true,
            isDebuggingAttempted = false,
            elapsedTime = 0L,
            formattedElapsedTime = "00:00:00"
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

        debugSessionCount++ // Increment session count for the next debugging session
        clearData() // Clear sensor data after stopping
    }



    // Clear the collected sensor data
    private fun clearData() {
        stepCounterList.clear()
        accelerometerList.clear()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                val steps = event.values[0]
                if (_state.value.isDebugging) {
                    val currentStep = steps - _state.value.totalSteps
                    stepCounterList.add(StepCountData(currentStep, System.currentTimeMillis()))
                    _state.value = _state.value.copy(currentStep = currentStep)
                } else {
                    _state.value = _state.value.copy(totalSteps = steps)
                }
            }
            Sensor.TYPE_ACCELEROMETER -> {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                if (_state.value.isDebugging) {
                    accelerometerList.add(AccelerometerData(x, y, z, System.currentTimeMillis()))
                    _state.value = _state.value.copy(
                        accelerometerX = x,
                        accelerometerY = y,
                        accelerometerZ = z
                    )
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }




    // Format the elapsed time into "HH:MM:SS" format
    private fun formatElapsedTime(elapsedMillis: Long): String {
        val hours = (elapsedMillis / 3600000) % 24
        val minutes = (elapsedMillis / 60000) % 60
        val seconds = (elapsedMillis / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
