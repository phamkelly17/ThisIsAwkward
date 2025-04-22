package com.example.thisisawkward

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thisisawkward.pages.CreateDateScreen
import com.example.thisisawkward.pages.HomeLoadingScreen
import com.example.thisisawkward.pages.HomeScreen
import com.example.thisisawkward.pages.LandingScreen
import com.example.thisisawkward.pages.LoginScreen
import com.example.thisisawkward.pages.MyProfileScreen
import com.example.thisisawkward.pages.OnDateScreen
import com.example.thisisawkward.pages.SignUpScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
                   composable("homeLoadingScreen"){
                       HomeLoadingScreen(navController)
                   }
                   composable("home"){
                       HomeScreen(navController)
                   }
                   composable("createDate"){
                       CreateDateScreen(navController)
                   }
                   composable("myProfile"){
                       MyProfileScreen(navController)
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