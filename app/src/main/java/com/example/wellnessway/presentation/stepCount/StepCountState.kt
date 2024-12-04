package com.example.wellnessway.presentation.stepCount

data class StepCountState(
    val currentStep: Float = 0f,
    val totalSteps: Float = 0f,
    val distanceInMeters: Double = 0.0,
    val caloriesBurned: Double = 0.0,
    val elapsedTime: Long = 0L,
    val formattedElapsedTime: String = "00:00:00",
    val isDebugging: Boolean = false,
    val isDebuggingAttempted: Boolean = false,
    val title: String = ""
)
