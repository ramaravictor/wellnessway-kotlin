package com.example.wellnessway.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.serialization.Serializable
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.wellnessway.R
import com.example.wellnessway.permission.LocationPermissionTextProvider
import com.example.wellnessway.permission.PermissionDialog
import com.example.wellnessway.permission.PermissionViewModel
import com.example.wellnessway.permission.openAppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@Serializable
object LocationRoute

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LocationScreen(
    navController: NavController? = null,
    vm: LocationViewModel = hiltViewModel(),
) {
    val permissionViewModel = viewModel<PermissionViewModel>()
    var addressText by remember { mutableStateOf("Fetching address...") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }


    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    val dialogQueue = permissionViewModel.visiblePermissionDialogQueue

    val state = vm.state.value
    val context = LocalContext.current

    var fusedLocationClient: FusedLocationProviderClient? = null

    // Background gradien
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEF5350), // Merah terang
            Color(0xFFD32F2F)  // Merah lebih gelap
        )
    )

    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            val areGranted = perms.values.reduce { acc, next -> acc && next }
            permissionsToRequest.forEach { permission ->
                permissionViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
            if (areGranted) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                }
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location: Location? ->
                    location?.let {
                        latitude = it.latitude
                        longitude = it.longitude
                        vm.getLocations(latitude, longitude)
                    }
                }?.addOnFailureListener {
                    Toast.makeText(context, "Failed to retrieve location", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        multiplePermissionResultLauncher.launch(permissionsToRequest)
    }

    LaunchedEffect(latitude, longitude) {
        if (latitude != null && longitude != null) {
            val address = getAddressFromLatLng(context, latitude!!, longitude!!)
            addressText = address ?: "Address not found"
            vm.getLocations(latitude!!, longitude!!)
        }
    }

    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = LocationPermissionTextProvider(),
                onDismiss = permissionViewModel::dismissDialog,
                onOkClick = {
                    permissionViewModel.dismissDialog()
                    multiplePermissionResultLauncher.launch(
                        arrayOf(permission)
                    )
                },
                onGoToAppSettingsClick = {
                    context.openAppSettings()
                }
            )
        }

            Column (modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(16.dp)
                        .background(Color(0x33FFCDD2), shape = RoundedCornerShape(16.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center // Mengatur konten ke tengah
                ) {
                    Text(
                        text = addressText,
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center // Mengatur teks di tengah secara horizontal
                    )
                }

                if (state.isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(state.list) { location ->
                            Card(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimary,
                                )

                            ) {
                                AsyncImage(
                                    model = location.imageUrl,
                                    contentDescription = "Psychologist Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                )
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        location.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(
                                        modifier = Modifier.height(4.dp)
                                    )
                                    Text(location.address)
                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        FilledTonalButton(
                                            onClick = {
                                                val gmmIntentUri =
                                                    Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
                                                val mapIntent =
                                                    Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                                mapIntent.setPackage("com.google.android.apps.maps")
                                                context.startActivity(mapIntent)
                                            },
                                            modifier = Modifier.weight(1f),
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_map_24),
                                                contentDescription = "Navigation",
                                                modifier = Modifier.width(24.dp)
                                            )
                                            Spacer(
                                                modifier = Modifier.width(4.dp)
                                            )
                                            Text("Navigation")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        }




suspend fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String? {
    return withContext(Dispatchers.IO) {
        try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val addressLines = (0..address.maxAddressLineIndex).map { address.getAddressLine(it) }
                addressLines.joinToString(separator = "\n")
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}