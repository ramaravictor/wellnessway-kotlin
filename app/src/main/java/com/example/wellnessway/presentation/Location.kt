package com.example.wellnessway.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LocationScreenold() {
    // Background gradient
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350), // Red gradient start
            Color(0xFFD32F2F)  // Red gradient end
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title bar
        Text(
            text = "Tempat Olahraga",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(16.dp))
                .padding(8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // List of locations
        LocationItem(
            name = "Embung Tambak Boyo",
            distance = "2km"
        )

        Spacer(modifier = Modifier.height(16.dp))

        LocationItem(
            name = "Garuda Fitness",
            distance = "2km"
        )
    }
}

@Composable
fun LocationItem(name: String, distance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // You can add an Image placeholder here later if needed.
            Spacer(modifier = Modifier.height(180.dp))

            Spacer(modifier = Modifier.height(8.dp))

            // Location information
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = distance, fontSize = 16.sp, fontWeight = FontWeight.Light)

                Spacer(modifier = Modifier.height(8.dp))

                // Navigasi button
                Button(
                    onClick = { /* TODO: Add navigation functionality */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFAFAF)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_directions),
                        contentDescription = "Navigasi",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Navigasi", color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    LocationScreenold()
}
