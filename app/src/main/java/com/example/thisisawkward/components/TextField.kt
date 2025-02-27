package com.example.thisisawkward.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thisisawkward.ui.theme.Gray
import com.example.thisisawkward.ui.theme.TextFieldGray

@OptIn(ExperimentalMaterial3Api::class) // Suppress experimental API warning
@Composable
fun TextField(
    label: String,
    fieldValue: MutableState<String>,
    onChange: (String) -> Unit,
    isPasswordField: Boolean = false,
    isNumberField: Boolean = false
    ) {
    Column (modifier = Modifier.padding(vertical = 4.dp)){
        Text(
            text = label,
            color = Color.White,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 4.dp)
        )

        OutlinedTextField(
            value = fieldValue.value,
            onValueChange = { newText -> onChange(newText) },
            shape = CircleShape,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White.copy(alpha = 0.4f), // Light blue background
                focusedBorderColor = Gray,
                unfocusedBorderColor = Gray,
            ),
            textStyle = TextStyle(color = TextFieldGray),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            keyboardOptions = if (isNumberField) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,

            visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}