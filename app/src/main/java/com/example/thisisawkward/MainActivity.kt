package com.example.thisisawkward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThisIsAwkwardTheme {
                val navController = rememberNavController()

               NavHost(
                   navController = navController,
                   startDestination = ("landing")
               ) {
                   composable("landing"){
                       LandingScreen(navController)
                   }
                   composable("login"){
                       LoginScreen(navController)
                   }
                   composable("signup"){
                       SignUpScreen(navController)
                   }
                   composable("home"){
                       HomeScreen(navController)
                   }
                   composable("createDate"){
                       CreateDateScreen()
                   }
                   composable("myProfile"){
                       MyProfileScreen()
                   }

               }
            }
        }
    }
}