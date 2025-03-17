package com.example.thisisawkward.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.Footer
import com.example.thisisawkward.components.Header
import com.example.thisisawkward.components.TextField
import com.example.thisisawkward.components.UploadImageButton
import com.example.thisisawkward.ui.theme.LightBlue

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
    var time = rememberSaveable { mutableStateOf("") }
    var date = rememberSaveable { mutableStateOf("") }
    var location = rememberSaveable { mutableStateOf("") }
    var modusOperandi = rememberSaveable { mutableStateOf("") }
    var additionalDetails = rememberSaveable { mutableStateOf("") }
    var imageUri = rememberSaveable { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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

            TextField(labelColor = Color.DarkGray, label = "Time", fieldValue = time, onChange = { time.value = it })
            TextField(labelColor = Color.DarkGray, label = "Date", fieldValue = date, onChange = { date.value = it })
            TextField(labelColor = Color.DarkGray, label = "Location", fieldValue = location, onChange = { location.value = it })
            TextField(labelColor = Color.DarkGray, label = "Preferred Modus Operandi", fieldValue = modusOperandi, onChange = { modusOperandi.value = it })
            TextField(labelColor = Color.DarkGray, label = "Additional Details", fieldValue = additionalDetails, onChange = { additionalDetails.value = it }, numLines = 2)

            UploadImageButton()

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Handle form submission */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
            ) {
                Text(text = "Submit", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}