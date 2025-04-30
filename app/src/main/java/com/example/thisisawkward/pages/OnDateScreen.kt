package com.example.thisisawkward.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.Footer
import com.example.thisisawkward.components.Header
import com.example.thisisawkward.components.OnDateAnimation
import com.example.thisisawkward.ui.theme.ButtonPink
import com.example.thisisawkward.ui.theme.ButtonRed
import com.example.thisisawkward.ui.theme.LightBlue
import com.example.thisisawkward.viewmodels.DateViewModel


//@Preview
//@Composable
//fun PreviewOnDateScreen() {
//    OnDateScreen(rememberNavController())
//}

@Composable
fun OnDateScreen(navController: NavController, dateId: String) {
    val dateViewModel: DateViewModel = viewModel()

    fun onCreateAlert() {
        dateViewModel.createDateAlert(dateId)
    }

    Background(id = R.drawable.background)
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Header()
        Spacer(modifier = Modifier.weight(1f))
        OnDateAnimation()
        Spacer(modifier = Modifier.weight(1f))
        ActionButtons(onCreateAlert = { onCreateAlert() })
        Spacer(modifier = Modifier.weight(2f))
        Footer(navController)
    }
}

@Composable
fun ActionButtons(
    onCreateAlert: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 80.dp,
    navController: NavController
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val arrangementOffset = (buttonSize * 1.2f) // Spacing adjustment

        // Top button
        Box(
            modifier = Modifier
                .offset(y = 0.dp)
                .size(buttonSize)
                .clip(CircleShape)
                .background(LightBlue)
                .clickable { /* Handle click */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.Call,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(buttonSize * .5f) // Adjust size as needed
            )
        }

        // Bottom-left button
        Box(
            modifier = Modifier
                .offset(x = -arrangementOffset, y = arrangementOffset / 2)
                .size(buttonSize)
                .clip(CircleShape)
                .background(ButtonPink)
                .clickable { /* Handle click */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.material_symbols_outlined_chat_bubble), // Replace with your drawable resource
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(buttonSize * .5f) // Adjust size as needed
            )
        }

        // Bottom-right button
        Box(
            modifier = Modifier
                .offset(x = arrangementOffset, y = arrangementOffset / 2)
                .size(buttonSize)
                .clip(CircleShape)
                .background(ButtonRed)
                .clickable { onCreateAlert() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.material_symbols_outlined_directions_walk), // Replace with your drawable resource
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(buttonSize * .5f) // Adjust size as needed
            )
        }
    }
}
