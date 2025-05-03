package com.example.thisisawkward.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Alert
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.Footer
import com.example.thisisawkward.components.Header
import com.example.thisisawkward.viewmodels.DateViewModel
import com.example.thisisawkward.viewmodels.LocationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController, locationViewModel: LocationViewModel) {
    val dateViewModel: DateViewModel = viewModel()

    val currentLng by locationViewModel.currentLng.collectAsState()
    val currentLat by locationViewModel.currentLat.collectAsState()

    val dates = rememberSaveable {
        mutableStateOf<List<Map<String, Any>>>(emptyList())
    }
    LaunchedEffect(Unit) {
        dateViewModel.getDates { dateList ->
            dates.value = dateList
        }
    }

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
            items(dates.value) { date ->
                Alert(date, currentLat, currentLng)
            }
        }
        Footer(navController)
    }
}