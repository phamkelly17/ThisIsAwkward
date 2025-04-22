package com.example.thisisawkward.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thisisawkward.R
import com.example.thisisawkward.ui.theme.ButtonGreen
import com.example.thisisawkward.ui.theme.ButtonRed
import com.example.thisisawkward.ui.theme.Maroon
import com.example.thisisawkward.viewmodels.DateViewModel

@Composable
fun Alert(date: Map<String, Any>) {
    var requestAccepted = rememberSaveable { mutableStateOf(false) }
    val id = date["id"] as? String ?: ""
    val userId = date["userId"] as? String ?: ""
    val name = date["name"]
    val location = date["location"]
    val modus = date["modusOperandi"]
    val contact = date["phone"]
    val additionalDetails = date["additionalDetails"]

    val dateViewModel: DateViewModel = viewModel()

    fun handleAcceptDateRequest () {
        requestAccepted.value = !requestAccepted.value
        dateViewModel.acceptDateRequest(id, userId, requestAccepted.value)
    }

    Box(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.sample_alert_image),
            contentDescription = "alertImage",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .height(600.dp)
                .width(360.dp)
        )
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
                    Row (modifier = Modifier.padding(horizontal = 10.dp)){
                        Text("ETA:", fontWeight = FontWeight.Bold, color = Maroon)
                        Text("100m away, 2 min walk", modifier = Modifier.padding(horizontal = 5.dp), color = Maroon)
                    }
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(vertical = 10.dp, horizontal = 20.dp),
        ) {
            Column {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White.copy(alpha = 0.6f))
                    .wrapContentSize()
                    .requiredWidth(350.dp)
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