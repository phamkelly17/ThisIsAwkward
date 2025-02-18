package com.example.thisisawkward.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thisisawkward.R

@Composable
fun Footer(navController: NavController) {
    Box(modifier = Modifier
        .background(Color.White)
        .height(90.dp)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.home_button),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(85.dp)
                    .padding(horizontal = 13.dp)
                    .clickable {
                        navController.navigate("home")
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.add_date_button),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(85.dp)
                    .padding(horizontal = 13.dp)
                    .clickable {
                        navController.navigate("createDate")
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.my_profile_button),
                contentDescription = "logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(85.dp)
                    .padding(horizontal = 13.dp)
                    .clickable {
                        navController.navigate("myProfile")
                    }
            )
        }
    }
}