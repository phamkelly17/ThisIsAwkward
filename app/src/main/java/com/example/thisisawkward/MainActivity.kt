package com.example.thisisawkward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thisisawkward.pages.CreateDateScreen
import com.example.thisisawkward.pages.HomeScreen
import com.example.thisisawkward.pages.LandingScreen
import com.example.thisisawkward.pages.LoginScreen
import com.example.thisisawkward.pages.MyProfileScreen
import com.example.thisisawkward.pages.OnDateScreen
import com.example.thisisawkward.pages.SignUpScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

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
                       LoginScreen(navController, auth)
                   }
                   composable("signup"){
                       SignUpScreen(navController, auth, db)
                   }
                   composable("home"){
                       HomeScreen(navController)
                   }
                   composable("createDate"){
                       CreateDateScreen(navController, auth, db)
                   }
                   composable("myProfile"){
                       MyProfileScreen(navController)
                   }
                   composable("onDate"){
                       OnDateScreen(navController)
                   }

               }
            }
        }
    }
}