package com.example.wellnessway.presentation.step_count

data class StepCountState(
    val isDebugging: Boolean = false,
    val isDebuggingAttempted: Boolean = false,
    val totalSteps: Float = 0f,
    val currentStep: Float = 0f,
    val accelerometerX: Float = 0f,
    val accelerometerY: Float = 0f,
    val accelerometerZ: Float = 0f,
    val title: String = "",
    val elapsedTime: Long = 0L,
    val formattedElapsedTime: String = "00:00:00"
)