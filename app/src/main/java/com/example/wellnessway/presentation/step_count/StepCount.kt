package com.example.wellnessway.presentation.step_count

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable


@Serializable
object StepCountRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StepCountScreen(navController: NavHostController) {
    // State untuk input step goals (TextFieldValue untuk menjaga state kursor)
    var selectedGoal by remember { mutableStateOf(TextFieldValue("1000")) } // Default goal

    // State untuk loading status dan progress
    var isLoading by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var buttonText by remember { mutableStateOf("Start") }
    val coroutineScope = rememberCoroutineScope() // Coroutine scope untuk mengontrol job
    var job: Job? by remember { mutableStateOf(null) } // Menyimpan job animasi

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
        // TextField untuk input Step Goals yang bisa diedit (hanya angka) dengan teks rata kanan
        TextField(
            value = selectedGoal,
            onValueChange = { newGoal ->
                if (newGoal.text.all { it.isDigit() }) { // Mengakses teks dari newGoal
                    selectedGoal = newGoal
                }
            },
            label = { Text("Step goals", color = Color.Gray) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFFCDD2),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black // Tetap hitam ketika tidak fokus
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number // Hanya mengizinkan angka
            ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End, // Menempatkan input di sisi kanan
                fontSize = 20.sp // Ukuran teks bisa disesuaikan
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown, // Ikon dropdown
                    contentDescription = "Dropdown Icon",
                    tint = Color.Black
                )
            }
        )


        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Bagian kiri: Informasi tambahan
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Move",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "11/240 KKAL",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Total steps",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "437",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Distance",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.White
                )
                Text(
                    text = "2.33 km",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Spacer untuk memberikan jarak antara informasi dan lingkaran loading
            Spacer(modifier = Modifier.width(16.dp))

            // Bagian kanan: Loading circle
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                // Lingkaran yang belum terpenuhi (background)
                CircularProgressIndicator(
                    progress = 1f, // Menampilkan lingkaran penuh
                    color = Color(0x33D32F2F), // Merah gelap transparan
                    strokeWidth = 40.dp,
                    modifier = Modifier.size(250.dp)
                )

                // Menampilkan lingkaran progress (bagian yang sudah terpenuhi)
                CircularProgressIndicator(
                    progress = progress,
                    color = Color.White, // Warna putih untuk progress
                    strokeWidth = 40.dp,
                    modifier = Modifier
                        .size(250.dp)
                        .graphicsLayer {
                            rotationZ = 360f // Mengubah posisi lingkaran agar dimulai dari atas (12 jam)
                        }
                )

                // Tampilkan persentase langkah
                Text(
                    text = "45%",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        // Tombol Start/Stop
        Button(
            onClick = {
                if (isLoading) {
                    isLoading = false
                    buttonText = "Start"
                    job?.cancel() // Hentikan coroutine
                } else {
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

        Spacer(modifier = Modifier.height(16.dp))

        // Daftar rekaman langkah
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
                Text(text = "1500 out of 2000 steps (75%)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
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
                Text(text = "1000 out of 1000 steps (100%)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Sabtu 06", fontSize = 14.sp, color = Color.Gray)
                    Text(text = "8:20 AM", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
