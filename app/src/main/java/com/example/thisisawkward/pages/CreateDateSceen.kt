package com.example.thisisawkward.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.DatePickerField
import com.example.thisisawkward.components.Footer
import com.example.thisisawkward.components.Header
import com.example.thisisawkward.components.TextField
import com.example.thisisawkward.components.TimePickerField
import com.example.thisisawkward.components.UploadImageButton
import com.example.thisisawkward.ui.theme.LightBlue
import com.example.thisisawkward.viewmodels.DateViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.thisisawkward.components.convertMillisToDate
import com.example.thisisawkward.components.formatTime
import com.example.thisisawkward.viewmodels.GeocodingViewModel
import com.example.thisisawkward.viewmodels.ProfileViewModel

@Preview
@Composable
fun PreviewCreateDateScreen() {
    CreateDateScreen(rememberNavController())
}

@Composable
fun CreateDateScreen(navController: NavController) {
    Background(id = R.drawable.background)
    Column(modifier = Modifier.fillMaxSize()){
        Header()
        DateForm()
        Spacer(modifier = Modifier.weight(1f))
        Footer(navController)
    }
}

@Composable
fun DateForm() {
    var startTime = rememberSaveable { mutableStateOf("") }
    var endTime = rememberSaveable { mutableStateOf("") }
    var date = rememberSaveable { mutableStateOf("") }
    var location = rememberSaveable { mutableStateOf("") }
    var modusOperandi = rememberSaveable { mutableStateOf("") }
    var additionalDetails = rememberSaveable { mutableStateOf("") }
    var imageUri = rememberSaveable { mutableStateOf<String?>(null) }
    var errorMessage = rememberSaveable { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var selectedStartHour by remember { mutableStateOf<Int?>(null) }
    var selectedStartMinute by remember { mutableStateOf<Int?>(null) }
    var selectedEndHour by remember { mutableStateOf<Int?>(null) }
    var selectedEndMinute by remember { mutableStateOf<Int?>(null) }

    val dateViewModel: DateViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val geocodingViewModel: GeocodingViewModel = viewModel()
    val locationErrorMessage by geocodingViewModel.locationErrorMessage.collectAsState()

    val context = LocalContext.current

    fun submitDate () {
        geocodingViewModel.geocodeAddress(context, location.value) {
            if (geocodingViewModel.locationErrorMessage.value == "") {
                dateViewModel.createDate(
                    startTime,
                    endTime,
                    date,
                    location,
                    modusOperandi,
                    additionalDetails,
                    errorMessage,
                    geocodingViewModel.lat.value,
                    geocodingViewModel.lng.value
                )

                selectedDate = null
                selectedStartHour = null
                selectedStartMinute = null
                selectedEndHour = null
                selectedEndMinute = null

                profileViewModel.incrementStat("totalDates", 1)
            }

            println("ERR MSG: ${geocodingViewModel.locationErrorMessage.value}")
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(625.dp)
            .padding(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()) // Enables scrolling
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Schedule Your Date!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            TimePickerField("Start Time", selectedStartHour, selectedStartMinute, onChange = { hour, minute ->
                selectedStartHour = hour
                selectedStartMinute = minute
                startTime.value = formatTime(hour, minute)
            })

            TimePickerField("End Time", selectedEndHour, selectedEndMinute, onChange = { hour, minute ->
                selectedEndHour = hour
                selectedEndMinute = minute
                endTime.value = formatTime(hour, minute)
            })

            DatePickerField(selectedDate = selectedDate, onChange = { millis ->
                date.value = convertMillisToDate(millis)
                selectedDate = millis
            })
            TextField(labelColor = Color.DarkGray, label = "Location", fieldValue = location, onChange = { location.value = it })
            if (locationErrorMessage.isNotEmpty()) {
                Text(locationErrorMessage, color = Color.Red, modifier = Modifier.align(Alignment.Start))
            }
            TextField(labelColor = Color.DarkGray, label = "Preferred Modus Operandi", fieldValue = modusOperandi, onChange = { modusOperandi.value = it })
            TextField(labelColor = Color.DarkGray, label = "Additional Details", fieldValue = additionalDetails, onChange = { additionalDetails.value = it }, numLines = 2)

            UploadImageButton()

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { submitDate() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
            ) {
                Text(text = "Submit", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}