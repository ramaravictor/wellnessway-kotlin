package com.example.wellnessway.component

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsModal(onDismissRequest: () -> Unit, navController: Any) {
    Dialog(onDismissRequest = onDismissRequest) {
        val gradientBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFEF5350), // Merah terang
                Color(0xFFD32F2F)  // Merah lebih gelap
            )
        )

        Surface(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxSize() // Mengisi seluruh layar
                .background(Color.Transparent)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Mengisi seluruh layar
                    .background(gradientBrush)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Sleep Logo",
                        tint = Color.White,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = "Bedtime",
                        fontWeight = FontWeight.Bold,
                        fontSize = 50.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Text(
                        text = "I usually go to bed around:",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
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
                    Text(
                        text = "Any sessions ending after this time will appear on the next day.",
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 32.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0x33FFCDD2), shape = RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
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
                        Switch(
                            checked = true,
                            onCheckedChange = {},
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Red
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Wake up at",
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
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

                Button(
                    onClick = { onDismissRequest() }, // Tutup modal setelah menekan tombol
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
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsModalPreview() {
    SettingsModal(navController = rememberNavController(), onDismissRequest = {})
}
