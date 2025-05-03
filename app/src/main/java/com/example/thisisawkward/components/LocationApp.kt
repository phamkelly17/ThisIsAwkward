package com.example.thisisawkward.components

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.thisisawkward.R
import com.example.thisisawkward.enableLocationManager
import com.example.thisisawkward.viewmodels.LocationViewModel

@Composable
fun LocationApp(navController: NavController, locationViewModel: LocationViewModel) {
    val context = LocalContext.current
    var location by remember { mutableStateOf("") }
    var permissionsGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionsGranted = isGranted
        if (!isGranted) {
            Toast.makeText(
                context,
                context.getString(R.string.permissions_denied),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Automatically launch permission request
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    if (permissionsGranted) {
        LaunchedEffect(Unit) {
            enableLocationManager(context) { loc ->
                location = context.getString(R.string.lat_long, loc.latitude, loc.longitude)
                locationViewModel.updateCurrentCoordinates(loc.latitude, loc.longitude)

                // Navigate to landing screen after location is granted
                navController.navigate("homeLoadingScreen") {
                    popUpTo("location") { inclusive = true } // Remove LocationApp from backstack
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}