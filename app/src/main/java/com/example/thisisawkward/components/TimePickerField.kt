package com.example.thisisawkward.components

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
fun TimePickerField(
    label: String,
    selectedHour: Int?,
    selectedMinute: Int?,
    onChange: (Int, Int) -> Unit,
) {


    var showDialog by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            color = Color.DarkGray,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 4.dp)
        )

        OutlinedTextField(
            value = if (selectedHour != null && selectedMinute != null) formatTime(selectedHour ?: 0, selectedMinute ?: 0) else "",
            onValueChange = { },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select time")
                }
            },
            shape = RoundedCornerShape((45 / 2).dp),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showDialog) {
        TimePickerDialog(
            onDismiss = { showDialog = false },
            onTimeSelected = { hour, minute ->
                onChange(hour, minute)
            }
        )
    }
}

fun formatTime(hour: Int, minute: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, minute)
    return android.text.format.DateFormat.format("hh:mm a", calendar).toString()
}