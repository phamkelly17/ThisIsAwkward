package com.example.thisisawkward.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thisisawkward.components.LoginBackground
import com.example.thisisawkward.components.TextField
import com.example.thisisawkward.ui.theme.Gray2
import com.example.thisisawkward.ui.theme.Maroon

@Composable
fun LoginScreen(navController: NavController) {
    var loginField = rememberSaveable { mutableStateOf("") }
    var passwordField = rememberSaveable { mutableStateOf("") }

    fun onLoginChange (newValue: String) {
        loginField.value = newValue
    }

    fun onPasswordChange (newValue: String) {
        passwordField.value = newValue
    }


    LoginBackground()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome!",
            style = TextStyle(fontSize = 36.sp),
            color = Maroon,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 60.dp, bottom = 20.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .drawBehind {
                    drawLine(
                        color = Color.White, // Border color
                        start = androidx.compose.ui.geometry.Offset(0f, 0f), // Start from top-left
                        end = androidx.compose.ui.geometry.Offset(
                            size.width,
                            0f
                        ), // End at top-right
                        strokeWidth = 1.dp.toPx() // Border thickness
                    )
                }
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp)
                ) {
                TextField(label = "E-mail", fieldValue = loginField, onChange = ::onLoginChange)
                TextField(label = "Password", fieldValue = passwordField, onChange = ::onPasswordChange, isPasswordField = true)
                Text(
                    text = "Forgot Password?",
                    color = Gray2,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 5.dp)
                        .clickable {
                        // Handle click here
                        navController.navigate("home")
                    }
                )

                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Maroon,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .width(350.dp)
                        .height(60.dp)
                ) {
                    Text(
                        text = "Log in",
                        style = TextStyle(fontSize = 24.sp)
                    )
                }

                // Spacer to push the Row down
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("New here? ", color = Color.White, fontSize = 20.sp)

                    Text(
                        text = "Sign Up",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable {
                            navController.navigate("signup")
                            }
                    )
                }
            }
        }
    }
}