package com.example.thisisawkward.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thisisawkward.BuildConfig
import com.example.thisisawkward.ui.theme.ButtonGreen
import com.example.thisisawkward.ui.theme.ButtonRed
import com.example.thisisawkward.ui.theme.Maroon
import com.example.thisisawkward.viewmodels.DateViewModel
import com.example.thisisawkward.viewmodels.ProfileViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.model.LatLng as GMapsLatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.PendingResult
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.model.DirectionsResult
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.TravelMode

@Composable
fun Alert(date: Map<String, Any>, currentLat: Double, currentLng: Double) {
    val acceptedBy = date["acceptedBy"]
    val requestAccepted = rememberSaveable { mutableStateOf(acceptedBy != "")}
    val id = date["id"] as? String ?: ""
    val userId = date["userId"] as? String ?: ""
    val name = date["name"]
    val location = date["location"]
    val modus = date["modusOperandi"]
    val contact = date["phone"]
    val additionalDetails = date["additionalDetails"]
    val dateLng = (date["lng"] as? Double) ?: 0.0
    val dateLat = (date["lat"] as? Double) ?: 0.0

    val currentLocation = LatLng(currentLat, currentLng)
    val dateLocation = LatLng(dateLat, dateLng)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 15f)
    }

    val dateViewModel: DateViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()

    fun getDirections(
        originLng: Double,
        originLat: Double,
        destinationLng: Double,
        destinationLat: Double,
        callback: (DirectionsResult?) -> Unit
    ) {
        val context = GeoApiContext.Builder()
            .apiKey(BuildConfig.MAPS_API_KEY)
            .build()

        // Convert from Android LatLng to Google Maps Java Client LatLng
        val originGMaps = GMapsLatLng(originLat, originLng)
        val destinationGMaps = GMapsLatLng(destinationLat, destinationLng)

        DirectionsApi.newRequest(context)
            .mode(TravelMode.DRIVING)
            .origin(originGMaps)
            .destination(destinationGMaps)
            .alternatives(true)
            .setCallback(object : PendingResult.Callback<DirectionsResult> {
                override fun onResult(result: DirectionsResult) {
                    callback(result)
                }

                override fun onFailure(e: Throwable) {
                    callback(null)
                }
            })
    }

    // State for DirectionsResult
    var directionsResult by remember { mutableStateOf<DirectionsResult?>(null) }

    // Fetch directions when the composable is first launched
    LaunchedEffect(currentLat, currentLng, dateLat, dateLng) {
        getDirections(currentLng, currentLat, dateLng,dateLat) { result ->
            directionsResult = result
        }
    }

    @Composable
    fun DrawRoute(route: DirectionsRoute) {
        // Convert the polyline points to LatLng objects
        val pathPoints = remember(route) {
            route.overviewPolyline.decodePath().map { LatLng(it.lat, it.lng) }
        }
        // Draw the polyline
                Polyline(
                    points = pathPoints,
                    color = Color.Blue,
                    width = 8f
                )
            }

    fun handleAcceptDateRequest () {
        requestAccepted.value = !requestAccepted.value
        dateViewModel.acceptDateRequest(id, userId, requestAccepted.value)
        val increment = if (requestAccepted.value) 1.toLong() else -1
        profileViewModel.incrementStat("datesCrashed", increment)
        profileViewModel.incrementCrashedDates(userId, increment)
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .align(Alignment.Center)
            .width(360.dp)
            .height(600.dp)
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ){
            GoogleMap(
                modifier = Modifier.fillMaxWidth(),
                cameraPositionState = cameraPositionState
            ) {
                // Only draw the route if directionsResult is available
                directionsResult?.let { result ->
                    val route = result.routes.firstOrNull()
                    route?.let {
                        DrawRoute(route)
                    }
                }
                Marker(
                    state = MarkerState(position = currentLocation),
                    title = "Your location",
                )

                Marker(
                    state = MarkerState(position = dateLocation),
                    title = "${name}'s location",
                )
            }

            Box(modifier = Modifier
                .align(Alignment.TopStart)
                .padding(vertical = 10.dp, horizontal = 20.dp),
            ){
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White.copy(alpha = 0.6f))
                    .wrapContentSize()
                    .requiredWidth(250.dp)
                ){
                    Column (modifier = Modifier.padding(vertical = 5.dp)){
                        Row (modifier = Modifier.padding(horizontal = 10.dp)){
                            Text("Name:", fontWeight = FontWeight.Bold, color = Maroon)
                            Text(text = "$name", modifier = Modifier.padding(horizontal = 5.dp), color = Maroon)
                        }
                        Row (modifier = Modifier.padding(horizontal = 10.dp)){
                            Text("Location:", fontWeight = FontWeight.Bold, color = Maroon)
                            Text("$location", modifier = Modifier.padding(horizontal = 5.dp), color = Maroon)
                        }
                    }
                }
            }
            Box(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(vertical = 10.dp, horizontal = 20.dp),
            ) {
                Column {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = Color.White.copy(alpha = 0.6f))
                        .wrapContentSize()
                        .requiredWidth(265.dp)
                        .padding(horizontal = 5.dp)
                    ){
                        Column (modifier = Modifier.padding(vertical = 5.dp)){
                            Row (modifier = Modifier.padding(horizontal = 10.dp)){
                                Text("Modus:", fontWeight = FontWeight.Bold, color = Maroon)
                                Text("$modus", modifier = Modifier.padding(horizontal = 5.dp), color = Maroon)
                            }
                            Row (modifier = Modifier.padding(horizontal = 10.dp)){
                                Text("Contact:", fontWeight = FontWeight.Bold, color = Maroon)
                                Text("$contact", modifier = Modifier.padding(horizontal = 5.dp), color = Maroon)
                            }
                            Column (modifier = Modifier.padding(horizontal = 10.dp)){
                                Text("Additional Details:", fontWeight = FontWeight.Bold, color = Maroon)
                                Text("$additionalDetails", modifier = Modifier.padding(horizontal = 5.dp), color = Maroon)
                            }
                        }
                    }
                    Button(
                        onClick = { handleAcceptDateRequest() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (requestAccepted.value) ButtonRed else ButtonGreen,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 5.dp)
                    ) {
                        Text(text = if (requestAccepted.value) "Cancel Request" else "Accept Request", color = Maroon)
                    }
                }
            }

        }
    }
}