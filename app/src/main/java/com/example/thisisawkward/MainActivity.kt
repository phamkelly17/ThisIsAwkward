package com.example.thisisawkward

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thisisawkward.components.LocationApp
import com.example.thisisawkward.pages.CreateDateScreen
import com.example.thisisawkward.pages.HomeLoadingScreen
import com.example.thisisawkward.pages.HomeScreen
import com.example.thisisawkward.pages.LandingScreen
import com.example.thisisawkward.pages.LoginScreen
import com.example.thisisawkward.pages.MyProfileScreen
import com.example.thisisawkward.pages.OnDateScreen
import com.example.thisisawkward.pages.SignUpScreen
import com.example.thisisawkward.viewmodels.LocationViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ThisIsAwkwardTheme {
                val navController = rememberNavController()

                val locationViewModel: LocationViewModel = viewModel()

                NavHost(
                   navController = navController,
                   startDestination = ("landing"),
               ) {
                   composable("landing"){
                       LandingScreen(navController)
                   }
                   composable("location") {
                       LocationApp(navController, locationViewModel)
                   }
                   composable("login"){
                       LoginScreen(navController)
                   }
                   composable("signup"){
                       SignUpScreen(navController)
                   }
                   composable("homeLoadingScreen"){
                       HomeLoadingScreen(navController)
                   }
                   composable("home"){
                       HomeScreen(navController, locationViewModel)
                   }
                   composable("createDate"){
                       CreateDateScreen(navController)
                   }
                   composable("myProfile"){
                       MyProfileScreen(navController, locationViewModel)
                   }
                   composable(
                       "onDate/{dateId}"
                   ) { backStackEntry ->
                       val dateId = backStackEntry.arguments?.getString("dateId") ?: ""
                       OnDateScreen(navController, dateId)
                   }

               }
            }
        }
    }
}