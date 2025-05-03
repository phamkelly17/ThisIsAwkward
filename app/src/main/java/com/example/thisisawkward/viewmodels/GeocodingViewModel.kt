package com.example.thisisawkward.viewmodels

import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class GeocodingViewModel : ViewModel() {
    private val _lng = MutableStateFlow(0.0)
    private val _lat = MutableStateFlow(0.0)

    val lng: StateFlow<Double> = _lng
    val lat: StateFlow<Double> = _lat

    private val _locationErrorMessage = MutableStateFlow("")
    val locationErrorMessage: StateFlow<String> = _locationErrorMessage

    fun geocodeAddress(context: Context, address: String, onFinished: () -> Unit) {
        _lat.value = 0.0
        _lng.value = 0.0
        _locationErrorMessage.value = ""

        viewModelScope.launch {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocationName(address, 1)
                if (addresses?.isNotEmpty() == true) {
                    val location = addresses[0]
                    _lat.value = location.latitude
                    _lng.value = location.longitude
                    _locationErrorMessage.value = ""
                }
                else {
                    _locationErrorMessage.value = "Invalid address."

                }
            } catch (e: Exception) {
                setLocationErrorMessage("Invalid address.")
            }  finally {
                onFinished()
            }
        }
    }

    private fun setLocationErrorMessage(message: String?) {
        _locationErrorMessage.value = message.orEmpty()
    }
}