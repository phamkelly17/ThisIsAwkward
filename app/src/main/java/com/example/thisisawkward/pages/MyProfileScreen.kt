package com.example.thisisawkward.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Footer

/*
* TODO:
*  add footer
* include map feature
* make interactable
* make scrollable
 */

@Composable
fun MyProfileScreen(navController: NavController) {
    var modusOperandum by remember { mutableStateOf("Strategic crashing expert.\nMaster of blending into any scene.") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(30.dp)) // Pushes everything down to avoid status bar overlap

        Box(
            modifier = Modifier.height(687.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            // Profile Picture as Background
            Image(
                painter = painterResource(id = R.drawable.sample_alert_image), // Replace with actual image
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp) // Adjusted height for better visibility
            )

            // Edit Icon for Profile Picture (without white ring)
            IconButton(
                onClick = { /* TODO: Open edit profile picture dialog */ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Profile Picture",
                    tint = Color.White
                )
            }

            // Scrollable White Box Background
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 270.dp) // Moved lower to expose more background
                    .clip(RoundedCornerShape(30.dp)) // Rounded corners applied
                    //.weight(1f) // Makes the content inside scrollable
                    .verticalScroll(scrollState), // Fixes scrolling
                color = Color.White,
                shadowElevation = 6.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Centered Name Row with Settings Icon
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "YOUR NAME",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(
                            onClick = { /* TODO: Navigate to Settings */ },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings",
                                tint = Color.Gray
                            )
                        }
                    }

                    Text(
                        text = "Joined April 25, 2025",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Stats Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStat("Dates Crashed", "17")
                        ProfileStat("Crashed Dates", "4")
                        ProfileStat("Total Dates", "6")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Modus Operandum Section
                    Column(Modifier.fillMaxWidth()) {
                        Text(text = "Modus Operandum:", fontWeight = FontWeight.Bold)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .height(70.dp) // Increased height for two-line text
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = modusOperandum,
                                fontSize = 16.sp,
                                color = Color.Black,
                                modifier = Modifier.weight(1f) // Ensures text takes most of the space
                            )
                            IconButton(
                                onClick = { /* TODO: Open edit dialog */ },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit Modus Operandum",
                                    tint = Color.DarkGray
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Map Section
                    Column(Modifier.fillMaxWidth()) {
                        Text(text = "Map of Dates:", fontWeight = FontWeight.Bold)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Gray) // Placeholder color for map
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sample_alert_image), // Replace with actual map image
                                contentDescription = "Map",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(100.dp)) // Extra spacing for scroll effect
                }
            }
        }
        Footer(navController)
    }
}

// Composable function for displaying a single stat box
@Composable
fun ProfileStat(label: String, value: String) {
    Column(
        modifier = Modifier
            .width(110.dp) // Uniform size
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.DarkGray)
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
