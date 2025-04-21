package com.example.thisisawkward.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thisisawkward.viewmodels.DateViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeLoadingScreen(navController: NavController) {
    val dateViewModel: DateViewModel = viewModel()
    val isOnDate = rememberSaveable { mutableStateOf<Boolean?>(null) } // null = loading
    val dateId = remember { mutableStateOf("") }

    // Use LaunchedEffect to call checkUserOnDate only once when the page loads
    LaunchedEffect(Unit) {
        dateViewModel.checkUserOnDate { res, id ->
            isOnDate.value = res
            dateId.value = id
        }
    }

    when (isOnDate.value) {
        true -> {
            LaunchedEffect(Unit) {
                navController.navigate("onDate/${dateId.value}") {
                    popUpTo("loading") { inclusive = true }
                }
            }
        }
        false -> {
            LaunchedEffect(Unit) {
                navController.navigate("home") {
                    popUpTo("loading") { inclusive = true }
                }
            }
        }
        null -> {
            CircularProgressIndicator()
        }
    }
}