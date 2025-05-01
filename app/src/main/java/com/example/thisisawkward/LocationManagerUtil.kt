package com.example.thisisawkward

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE

@SuppressLint("MissingPermission")
fun enableLocationManager(context: Context, onLocationUpdate: (Location) -> Unit) {
    val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
    val locationListener = LocationListener { location ->
        onLocationUpdate(location)
    }

    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        0L,
        1f,
        locationListener
    )
}