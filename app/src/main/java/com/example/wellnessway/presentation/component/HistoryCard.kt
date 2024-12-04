package com.example.wellnessway.presentation.component

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.example.wellnessway.data.local.schema.History
import kotlinx.serialization.json.JsonNull.content
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryCard(history: History, context: Context, onDeleteItemClicked: (History) -> Unit, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with Title and Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = history.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    val date = dateFormat.format(Date(history.timestamp))
                    Text(
                        text = "Date: $date",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black
                    )
                }
                IconButton(onClick = { onDeleteItemClicked(history) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), // Menambahkan padding horizontal agar Divider tidak memenuhi layar penuh
                color = Color.Black, // Warna Divider (opsional)
                thickness = 1.dp // Ketebalan Divider (opsional)
            )
            // Konten fleksibel yang dapat diatur dari luar

            content()

            // Display Sensor File Items with Icons
            SensorFileSection("Steps Data", history.stepCounterPath, context)
            }
    }
}

@Composable
fun SensorFileSection(label: String, filePath: String, context: Context) {
    if (filePath.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openFile(context, filePath) }
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // File Icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFFD32F2F), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.FileOpen, // Change to relevant icon for files
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // File Label
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun openFile(context: Context, filePath: String) {
    val file = File(filePath)
    if (!file.exists()) {
        Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show()
        return
    }

    val uri: Uri = FileProvider.getUriForFile(
        context,
        "com.example.wellnessway.provider",
        file
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "text/csv")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No application found to open this file.", Toast.LENGTH_SHORT).show()
    }
}
