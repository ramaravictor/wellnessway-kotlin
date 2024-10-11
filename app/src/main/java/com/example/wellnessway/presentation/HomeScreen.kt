package com.example.wellnessway.presentation

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wellnessway.presentation.location.LocationRoute
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
object HomeRoute

@Composable
fun HomeScreen(navController: NavHostController) {
    // Background gradien merah
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350), // Merah terang
            Color(0xFFD32F2F)  // Merah lebih gelap
        )
    )

    // Mengambil tanggal saat ini
    val calendar = Calendar.getInstance()
    val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.time) // Misalnya, "Saturday"
    val currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.time) // Misalnya, "14 September 2024"

    // Menggunakan Box untuk memposisikan konten di tengah layar
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush)
            .padding(vertical = 32.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Bagian atas dengan tanggal dan tombol pengaturan
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tanggal dan hari
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(
                        text = currentDay, // Menampilkan hari saat ini
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 44.sp
                        )
                    )
                    Text(
                        text = currentDate, // Menampilkan tanggal saat ini
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    )
                }
            }
        }

        // Teks "Your Wellness, Our Way" yang ditempatkan di tengah vertikal dan rata kiri
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Your",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Wellness,",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Our Way",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}
