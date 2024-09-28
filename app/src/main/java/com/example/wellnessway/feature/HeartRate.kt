package com.example.wellnessway.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HeartRateScreen() {
    // State untuk loading status dan progress
    var isLoading by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var buttonText by remember { mutableStateOf("Start") } // State untuk text tombol
    val coroutineScope = rememberCoroutineScope() // Coroutine scope untuk mengontrol job
    var job by remember { mutableStateOf<Job?>(null) } // Menyimpan job animasi

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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bagian monitoring heart rate dan loading circle
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            // Lingkaran yang belum terpenuhi (background)
            CircularProgressIndicator(
                progress = {
                    1f // Menampilkan lingkaran penuh
                },
                modifier = Modifier.size(250.dp),
                color = Color(0x33D32F2F), // Merah gelap transparan
                strokeWidth = 40.dp,
            )

            // Menampilkan lingkaran progress (bagian yang sudah terpenuhi)
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .size(250.dp)
                    .graphicsLayer {
                        rotationZ = 360f // Mengubah posisi lingkaran agar dimulai dari atas (12 jam)
                    },
                color = Color.White, // Warna putih untuk progress
                strokeWidth = 40.dp, // Membuat lingkaran lebih tebal
            )

            // Tampilkan konten di dalam lingkaran
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Angka detak jantung
                Text(
                    text = "64",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))

                // Tulisan "bpm"
                Text(
                    text = "bpm",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Tombol Start/Stop
        Button(
            onClick = {
                if (isLoading) {
                    // Jika sedang loading, hentikan animasi dan ubah tombol kembali ke Start
                    isLoading = false
                    buttonText = "Start"
                    job?.cancel() // Hentikan coroutine
                } else {
                    // Jika tidak sedang loading, mulai animasi dan ubah tombol ke Stop
                    isLoading = true
                    buttonText = "Stop"
                    job = coroutineScope.launch {
                        for (i in 0..100) {
                            progress = i / 100f // Mengatur progress dari 0 hingga 1
                            delay(200) // Delay 0.2 detik (total 20 detik)
                        }
                        isLoading = false
                        buttonText = "Start" // Kembali ke Start setelah selesai
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFCDD2)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = buttonText, color = Color.Black, fontSize = 18.sp) // Teks sesuai dengan state
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Daftar rekaman heart rate
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFCDD2), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            // Rekaman pertama
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "91 bpm", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Minggu 07", fontSize = 14.sp, color = Color.Gray)
                    Text(text = "8:20 AM", fontSize = 14.sp, color = Color.Gray)
                }
            }
            Divider(color = Color.Gray, thickness = 1.dp)
            // Rekaman kedua
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "80 bpm", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Jumat 05", fontSize = 14.sp, color = Color.Gray)
                    Text(text = "5:30 AM", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HeartRateScreenPreview() {
    HeartRateScreen()
}

