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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thisisawkward.R
import com.example.thisisawkward.components.Background
import com.example.thisisawkward.components.TextField
import com.example.thisisawkward.ui.theme.Gray2
import com.example.thisisawkward.ui.theme.Maroon

@Composable
fun SignUpScreen(navController: NavController) {
    var nameField = rememberSaveable { mutableStateOf("") }
    var ageField = rememberSaveable { mutableStateOf("") }
    var regionField = rememberSaveable { mutableStateOf("") }
    var emailField = rememberSaveable { mutableStateOf("") }
    var passwordField = rememberSaveable { mutableStateOf("") }
    var reenterPasswordField = rememberSaveable { mutableStateOf("") }
    var checked = rememberSaveable { mutableStateOf(false) }

    fun onNameChange (newValue: String) {
        nameField.value = newValue
    }

    fun onRegionChange (newValue: String) {
        regionField.value = newValue
    }

    fun onEmailChange (newValue: String) {
        emailField.value = newValue
    }

    fun onPasswordChange (newValue: String) {
        passwordField.value = newValue
    }

    fun onReenterPasswordChange (newValue: String) {
        reenterPasswordField.value = newValue
    }

    fun onAgeChange (newValue: String) {
        ageField.value = newValue
    }


    Background(id = R.drawable.signup_background)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome!",
            style = TextStyle(fontSize = 36.sp),
            color = Maroon,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .drawBehind {
                    drawLine(
                        color = Color.White, // Border color
                        start = Offset(0f, 0f), // Start from top-left
                        end = Offset(
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
                TextField(label = "Name", fieldValue = nameField, onChange = ::onNameChange)
                TextField(label = "Age", fieldValue = ageField, onChange = ::onAgeChange, isNumberField = true)
                TextField(label = "Region", fieldValue = regionField, onChange = ::onRegionChange)
                TextField(label = "Email", fieldValue = emailField, onChange = ::onEmailChange)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 20.dp)
                .drawBehind {
                    drawLine(
                        color = Color.White,
                        start = Offset(0f, 0f),
                        end = Offset(
                            size.width,
                            0f
                        ),
                        strokeWidth = 1.dp.toPx()
                    )
                }
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                TextField(label = "Password", fieldValue = passwordField, onChange = ::onPasswordChange, isPasswordField = true)
                TextField(label = "Re-enter password", fieldValue = reenterPasswordField, onChange = ::onReenterPasswordChange, isPasswordField = true)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Checkbox(
                        checked = checked.value,
                        onCheckedChange = { checked.value = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Maroon,
                            uncheckedColor = Color.White,
                            checkmarkColor = Color.White
                        )
                    )
                    Text(text = "Accept Terms & Conditions", color = Gray2)
                }
            }
        }

        Button(
            onClick = { navController.navigate("home") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Maroon,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(350.dp)
                .height(60.dp)
        ) {
            Text(
                text = "Sign up",
                style = TextStyle(fontSize = 24.sp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Have an account? ", color = Color.White, fontSize = 20.sp)

            Text(
                text = "Log In",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline,
                fontSize = 20.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate("login")
                    }
                    .padding(vertical = 5.dp)
            )
        }
    }
}