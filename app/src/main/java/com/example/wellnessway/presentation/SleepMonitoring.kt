package com.example.wellnessway.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.wellnessway.component.SettingsModal

@Composable
fun SleepMonitoringScreen(navController: NavController) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350), // Merah terang
            Color(0xFFD32F2F)  // Merah lebih gelap
        )
    )

    // State untuk mengontrol apakah modal terbuka atau tidak
    var isModalVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(vertical = 48.dp) // Padding atas dan bawah
            .padding(horizontal = 16.dp), // Padding kiri dan kanan
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row bagian atas untuk ikon settings
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { isModalVisible = true }) { // Ketika diklik, tampilkan modal
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White,
                    modifier = Modifier.size(70.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Spacer untuk memberikan ruang kosong di bagian atas

        // Informasi jam bangun yang diletakkan di kiri bawah
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), // Padding bawah agar tidak terlalu dekat dengan tombol
            horizontalAlignment = Alignment.Start // Menyelaraskan ke kiri
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Icon(
                    imageVector = Icons.Default.Notifications, // Ganti dengan ikon yang sesuai
                    contentDescription = "Alarm Icon",
                    tint = Color.White,
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Wake up at",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "07:00",
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Tombol Start yang diletakkan di bawah layar
        Button(
            onClick = {
                navController.navigate("sleep_start") // Navigasi ke SleepStartScreen
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFCDD2)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Start", color = Color.Black, fontSize = 18.sp)
        }
    }

    // Menampilkan modal jika isModalVisible bernilai true
    if (isModalVisible) {
        SettingsModal(
            navController = navController,
            onDismissRequest = { isModalVisible = false } // Mengubah state menjadi false untuk menutup modal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SleepMonitoringScreenPreview() {
    SleepMonitoringScreen(navController = rememberNavController())
}
