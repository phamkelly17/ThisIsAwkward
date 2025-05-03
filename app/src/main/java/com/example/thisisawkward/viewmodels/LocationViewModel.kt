package com.example.thisisawkward.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LocationViewModel : ViewModel() {
    private val _currentLng = MutableStateFlow(0.0)
    private val _currentLat = MutableStateFlow(0.0)

    val currentLng: StateFlow<Double> = _currentLng
    val currentLat: StateFlow<Double> = _currentLat

    fun updateCurrentCoordinates(lat: Double, lng: Double) {
        _currentLng.value = lng
        _currentLat.value = lat
    }

}