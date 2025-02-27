package com.example.thisisawkward.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Alert
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.Footer
import com.example.thisisawkward.components.Header

@Composable
fun HomeScreen(navController: NavController) {
    Background(id = R.drawable.background)
    Column(modifier = Modifier.fillMaxSize()){
        Header()
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            items(50) { _ ->
                Alert()
            }
        }
        Footer(navController)
    }
}