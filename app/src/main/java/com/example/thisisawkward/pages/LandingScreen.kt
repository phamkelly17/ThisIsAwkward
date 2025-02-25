package com.example.thisisawkward.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.LandingBackground
import com.example.thisisawkward.ui.theme.Maroon

@Composable
fun LandingScreen(navController: NavController) {
    LandingBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Save the day. Crash the date.",
            color = Maroon,
            modifier = Modifier.padding(10.dp)
        )
        Button(
            onClick = { navController.navigate("login") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Maroon,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(350.dp)
                .height(65.dp)
                .padding(vertical = 5.dp)
        ) {
            Text(
                text = "Log in",
                style = TextStyle(fontSize = 24.sp)
            )
        }
        Button(
            onClick = { navController.navigate("signup") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Maroon,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(350.dp)
                .height(65.dp)
                .padding(vertical = 5.dp)
        ) {
            Text(
                text = "Sign up",
                style = TextStyle(fontSize = 24.sp)
            )
        }
        Box(modifier = Modifier.padding(10.dp)){
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(30.dp)
            )
        }
    }
}