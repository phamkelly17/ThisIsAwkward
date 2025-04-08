package com.example.thisisawkward.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun DatePickerField(selectedDate: Long?, onChange: (Long) -> Unit,) {
    var showDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = "Date",
            color = Color.DarkGray,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 4.dp)
        )
        OutlinedTextField(
            value = selectedDate?.let { convertMillisToDate(it) } ?: "",
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                }
            },
            shape = RoundedCornerShape((45 / 2).dp),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showDialog) {
        DatePickerDialog(
            onDismiss = { showDialog = false },
            onDateSelected = { millis ->
                onChange(millis)
            }
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}