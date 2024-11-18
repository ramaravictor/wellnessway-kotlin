package com.example.wellnessway.presentation.step_count

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.wellnessway.component.BottomBar
import com.example.wellnessway.component.SensorCard
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Serializable
object StepCountRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepCountScreen(
    navController: NavHostController,
    viewModel: StepCountViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Sensor Debugging", color = MaterialTheme.colorScheme.onTertiary, fontSize = 20.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = MaterialTheme.colorScheme.onTertiary,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            )
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Indeterminate or determinate circular progress indicator based on debugging state
            if (state.isDebugging) {
                // Indeterminate CircularProgressIndicator when debugging
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.tertiary,
                    strokeWidth = 8.dp,
                    modifier = Modifier.size(200.dp)
                )
            } else {
                // Determinate CircularProgressIndicator when not debugging
                CircularProgressIndicator(
                    progress = 1f,
                    color = MaterialTheme.colorScheme.tertiary,
                    strokeWidth = 8.dp,
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Display sensor data in SensorCard components with different background colors
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SensorCard(
                    icon = Icons.Default.DirectionsWalk,
                    title = "Step Counter",
                    content = "Total Steps: ${state.currentStep}\nTime: ${state.formattedElapsedTime}",
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer
                )

                Spacer(modifier = Modifier.height(16.dp))

                SensorCard(
                    icon = Icons.Default.DeviceHub,
                    title = "Accelerometer",
                    content = "X: ${state.accelerometerX}\nY: ${state.accelerometerY}\nZ: ${state.accelerometerZ}",
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Start/Stop Debugging button
            Button(
                onClick = {
                    if (state.isDebugging) {
                        viewModel.stopDebugging()
                    } else {
                        viewModel.startDebugging()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isDebugging) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = if (state.isDebugging) "Stop Debugging" else "Start Debugging",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
