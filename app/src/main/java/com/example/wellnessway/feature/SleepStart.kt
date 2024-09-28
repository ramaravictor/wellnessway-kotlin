package com.example.wellnessway.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SleepStartScreen(navController: NavController) {
    // Background gradien
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
            .padding(vertical = 32.dp) // Padding atas dan bawah
            .padding(horizontal = 16.dp), // Padding kiri dan kanan
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                // Aksi ketika tombol diklik
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFCDD2)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Mengatur agar ikon dan teks berada di sisi kiri, tengah, dan kanan
            ) {
                // Icon Alarm dan jam
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications, // Ganti dengan ikon Notifikasi (alarm)
                        contentDescription = "Alarm Icon",
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "07:00",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                }

                // Icon tiga titik untuk pengaturan tambahan
                Icon(
                    imageVector = Icons.Default.MoreVert, // Ganti dengan ikon MoreVert (tiga titik)
                    contentDescription = "More Options",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bagian monitoring kualitas tidur dan lingkaran progress
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            // Lingkaran background
            CircularProgressIndicator(
                progress = 1f,
                color = Color(0x33D32F2F),
                strokeWidth = 40.dp,
                modifier = Modifier.size(250.dp)
            )

            // Lingkaran progress kualitas tidur
            CircularProgressIndicator(
                progress = 0.45f, // Placeholder nilai progress (45%)
                color = Color.White,
                strokeWidth = 40.dp,
                modifier = Modifier
                    .size(250.dp)
                    .graphicsLayer {
                        rotationZ = 360f // Dimulai dari posisi atas
                    }
            )

            // Tampilkan persentase kualitas tidur
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Quality",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "45%",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tampilkan waktu saat ini dan jam tersisa
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "21:27", // Placeholder waktu saat ini
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "7hrs 51min left", // Placeholder waktu tersisa
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Icon Stop dan Eye di bagian bawah
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    navController.navigate("sleep_monitoring")
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Background transparan
                    contentColor = Color.Black // Warna ikon
                ),
                modifier = Modifier
                    .padding(8.dp) // Menambah padding luar
                    .size(80.dp) // Ukuran total button
                    .border(2.dp, Color.Black, shape = CircleShape) // Border hitam di sekitar ikon
            ) {
                Icon(
                    imageVector = Icons.Default.Close, // Ganti dengan ikon Stop
                    contentDescription = "Stop Icon",
                    tint = Color.Black, // Warna ikon
                    modifier = Modifier.size(50.dp) // Ukuran ikon lebih besar di dalam border
                )
            }

            Button(
                onClick = { /* Aksi untuk tombol eye */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Background transparan
                    contentColor = Color.Black // Warna ikon
                ),
                modifier = Modifier
                    .padding(8.dp) // Menambah padding luar
                    .size(80.dp) // Ukuran total button
                    .border(2.dp, Color.Black, shape = CircleShape) // Border hitam di sekitar ikon
            ) {
                Icon(
                    imageVector = Icons.Default.Face, // Ganti dengan ikon Eye
                    contentDescription = "Eye Icon",
                    tint = Color.Black, // Warna ikon
                    modifier = Modifier.size(70.dp) // Ukuran ikon lebih besar di dalam border
                )
            }




        }
    }
}

@Preview(showBackground = true)
@Composable
fun SleepStartScreenPreview() {
    SleepStartScreen(navController = rememberNavController())
}