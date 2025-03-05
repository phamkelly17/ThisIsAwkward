package com.example.thisisawkward.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
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
    isNumberField: Boolean = false,
    numLines: Int = 1,
    labelColor: Color = Color.White
    ) {
    Column (modifier = Modifier.padding(vertical = 4.dp)){
        Text(
            text = label,
            color = labelColor,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 4.dp)
        )

        OutlinedTextField(
            value = fieldValue.value,
            onValueChange = { newText -> onChange(newText) },
            shape = RoundedCornerShape((45 / 2).dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.White.copy(alpha = 0.4f), // Light blue background
                focusedBorderColor = Gray,
                unfocusedBorderColor = Gray,
            ),
            textStyle = TextStyle(color = TextFieldGray),
            modifier = Modifier
                .fillMaxWidth()
                .height((25 + (20 * numLines)).dp),
            singleLine = (numLines == 1),
            keyboardOptions = if (isNumberField) KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number) else KeyboardOptions.Default,

            visualTransformation = if (isPasswordField) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}

@Preview
@Composable
fun PreviewTextField() {
    var text = rememberSaveable { mutableStateOf("") }
    TextField(
        label = "label",
        fieldValue = text,
        onChange = { text.value = it }
    )
}

@Preview
@Composable
fun PreviewTextFieldGrayLabel() {
    var text = rememberSaveable { mutableStateOf("") }
    TextField(
        label = "label",
        fieldValue = text,
        onChange = { text.value = it },
        labelColor = Color.DarkGray
    )
}

@Preview
@Composable
fun PreviewMultilineTextField() {
    var text = rememberSaveable { mutableStateOf("") }
    TextField(
        label = "label",
        fieldValue = text,
        onChange = { text.value = it },
        numLines = 5
    )
}