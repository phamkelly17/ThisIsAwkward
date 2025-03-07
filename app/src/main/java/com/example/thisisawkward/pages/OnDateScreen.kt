package com.example.thisisawkward.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.Footer
import com.example.thisisawkward.components.Header
import com.example.thisisawkward.components.OnDateAnimation

@Preview
@Composable
fun PreviewOnDateScreen() {
    OnDateScreen(rememberNavController())
}

@Composable
fun OnDateScreen(navController: NavController) {
    Background(id = R.drawable.background)
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Header()
        Spacer(modifier = Modifier.weight(1f))
        OnDateAnimation()
        Spacer(modifier = Modifier.weight(2f))
        Footer(navController)
    }
}