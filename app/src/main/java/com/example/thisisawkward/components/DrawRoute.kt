package com.example.thisisawkward.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import com.google.maps.model.DirectionsRoute

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