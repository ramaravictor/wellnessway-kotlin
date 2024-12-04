package com.example.wellnessway.presentation.stepCount

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.wellnessway.presentation.component.SensorCard
import kotlinx.serialization.Serializable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.decode.GifDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.wellnessway.R
import com.example.wellnessway.presentation.history.HistoryRoute
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Serializable
object StepCountRoute

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StepCountScreen(
    navController: NavHostController,
    viewModel: StepCountViewModel = viewModel(),
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350),
            Color(0xFFD32F2F)
        )
    )
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    val permissions = listOf(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) android.Manifest.permission.ACTIVITY_RECOGNITION else android.Manifest.permission.BODY_SENSORS,
        android.Manifest.permission.BODY_SENSORS
    )

    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)

    LaunchedEffect(permissionsState.allPermissionsGranted) {
        if (!permissionsState.allPermissionsGranted) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))

                if (permissionsState.allPermissionsGranted) {
                    if (state.isDebugging) {
                        AnimatedGif()
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(R.raw.walking_animation)
                                    .apply { setParameter("coil:decodeOnlyFirstFrame", true) }
                                    .build()
                            ),
                            contentDescription = "Walking Animation Stopped",
                            modifier = Modifier.size(170.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SensorCard(
                            icon = Icons.Default.DirectionsWalk,
                            title = "Step Counter",
                            content = {
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "Total Steps:",
                                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "${state.currentStep}",
                                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(
                                            text = "Time:",
                                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "${state.formattedElapsedTime}",
                                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                                            color = Color.Black
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(8.dp)
                                    ) {
                                        Text(
                                            text = "Calories:",
                                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "${state.caloriesBurned} kcal",
                                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Distance:",
                                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "${state.distanceInMeters} m",
                                            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                                            color = Color.Black
                                        )
                                    }
                                }
                            },
                            backgroundColor = Color.White,
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (state.isDebugging) {
                                viewModel.stopDebugging()
                                navController.navigate(HistoryRoute)
                            } else {
                                viewModel.startDebugging()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(70.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.isDebugging) Color(0x33FFCDD2) else Color.White
                        )
                    ) {
                        Text(
                            text = if (state.isDebugging) "Stop" else "Start",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Permissions required to access step counter",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                            Text(text = "Grant Permissions")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedGif() {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.raw.walking_animation)
            .decoderFactory(if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory() else GifDecoder.Factory())
            .size(Size.ORIGINAL)
            .build(),
        contentDescription = "Walking Animation",
        modifier = Modifier.size(170.dp)
    )
}
