package com.example.wellnessway.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350), // Merah terang
            Color(0xFFD32F2F)  // Merah lebih gelap
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start // Mengatur rata kiri untuk semua elemen dalam Column
        ) {
            // Logo
            Icon(
                imageVector = Icons.Default.Notifications, // Simbol Z (Ganti dengan logo tidur)
                contentDescription = "Sleep Logo",
                tint = Color.White,
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 16.dp) // Memberikan padding agar agak ke dalam dari kiri
            )

            // Title
            Text(
                text = "Bedtime",
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp) // Memberikan padding ke kiri
            )

            // Subtitle
            Text(
                text = "I usually go to bed around:",
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 16.dp) // Memberikan padding ke kiri
            )


            // Time input (22:00)
            Button(
                onClick = { /* Aksi Done */ },
                modifier = Modifier
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = "22:00", fontSize = 32.sp, color = Color.Black)
            }


            // Informasi tambahan
            Text(
                text = "Any sessions ending after this time will appear on the next day.",
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Start, // Sejajarkan teks ke kiri
                modifier = Modifier
                    .padding(start = 16.dp, end = 32.dp) // Menambahkan padding kiri dan kanan
            )
        }

        // Alarm setting
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x33FFCDD2), shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title row with Alarm label and switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Alarm Icon",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Alarm", fontSize = 24.sp, color = Color.Black)
                }

                // Switch
                Switch(
                    checked = true, // Modify as necessary
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Red
                    )
                )
            }

            // Wake up time setting
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Wake up time label
                Text(
                    text = "Wake up at",
                    fontSize = 24.sp,
                    color = Color.Black
                )

                // Wake up time and edit icon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "07:00",
                        fontSize = 24.sp,
                        color = Color.Black
                    )
                    IconButton(onClick = { /* Aksi edit */ }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Wake Up Time",
                            tint = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Done button
        Button(
            onClick = { /* Aksi Done */ },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(text = "Done", fontSize = 18.sp, color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}
