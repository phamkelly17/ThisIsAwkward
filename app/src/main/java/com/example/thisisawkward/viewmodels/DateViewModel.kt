package com.example.thisisawkward.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class DateViewModel : ViewModel() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    val user = auth.currentUser

    fun createDate(startTime: MutableState<String>,
                   endTime: MutableState<String>,
                   date: MutableState<String>,
                   location: MutableState<String>,
                   modusOperandi: MutableState<String>,
                   additionalDetails: MutableState<String>,
                   errorMessage: MutableState<String>
    ) {
        val dateData = hashMapOf(
            "startTime" to startTime.value,
            "endTime" to endTime.value,
            "date" to date.value,
            "location" to location.value,
            "modusOperandi" to modusOperandi.value,
            "additionalDetails" to additionalDetails.value,
            "alertCreated" to false,
        )

        user?.let {
            db.collection("users")
                .document(it.uid)
                .collection("dates")
                .add(dateData)
                .addOnSuccessListener {
                    startTime.value = ""
                    endTime.value = ""
                    date.value = ""
                    location.value = ""
                    modusOperandi.value = ""
                    additionalDetails.value = ""
                }
                .addOnFailureListener { errorMessage.value = it.localizedMessage }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkUserOnDate(callback: (Boolean) -> Unit) {
        var dateFound = false

        val today = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        val timeNow = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a") // 12-hour format with AM/PM

        user?.let {
            db.collection("users")
                .document(it.uid)
                .collection("dates")
                .get()
                .addOnSuccessListener { snapshot ->
                    for (doc in snapshot) {
                        val date = doc.getString("date")
                        val startTime = doc.getString("startTime")
                        val endTime = doc.getString("endTime")

                        if (startTime != null && endTime != null) {
                            try {
                                val start = LocalTime.parse(startTime, formatter)
                                val end = LocalTime.parse(endTime, formatter)

                                if (date == today && timeNow.isAfter(start) && timeNow.isBefore(end)){
                                    callback(true)
                                    return@addOnSuccessListener
                                }
                            }
                            catch (e: Exception) {
                                println("error")
                            }
                        }
                    }
                    callback(false)
                }
        } ?: callback(false) // If user is null
    }
}