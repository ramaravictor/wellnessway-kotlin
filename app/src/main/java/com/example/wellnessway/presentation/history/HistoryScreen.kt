package com.example.wellnessway.presentation.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wellnessway.presentation.component.HistoryCard
import kotlinx.serialization.Serializable


@Serializable
object HistoryRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = viewModel(),
    navController: NavController
) {
    // Background gradien merah
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350), // Merah terang
            Color(0xFFD32F2F)  // Merah lebih gelap
        )
    )
    val histories by viewModel.histories.collectAsState(initial = emptyList())
    val context = LocalContext.current

    // Gunakan Box untuk memastikan gradien mencakup seluruh layar
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush) // Terapkan gradien merah di latar belakang
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "History",
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color(0xFFD32F2F),
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )
            },
            containerColor = Color.Transparent // Pastikan Scaffold transparan
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                if (histories.isEmpty()) {
                    Text(
                        text = "No history available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFFFCDD2),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(histories) { history ->
                            HistoryCard(
                                history = history,
                                context = context,
                                content = {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        // Column for Total Steps
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Total Steps:",
                                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                                                color = Color.Black
                                            )
                                            Text(
                                                text = "1", // Replace with dynamic step value
                                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                                                color = Color.Black
                                            )
                                        }

                                        // Column for Calories
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Calories:",
                                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                                                color = Color.Black
                                            )
                                            Text(
                                                text = "0 kcal", // Replace with dynamic calorie value
                                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                                                color = Color.Black
                                            )
                                        }

                                        // Column for Distance
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = "Distance:",
                                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 12.sp),
                                                color = Color.Black
                                            )
                                            Text(
                                                text = "0 m", // Replace with dynamic distance value
                                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp),
                                                color = Color.Black
                                            )
                                        }
                                    }
                                },
                                onDeleteItemClicked = { viewModel.deleteHistory(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

