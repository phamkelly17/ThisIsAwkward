package com.example.thisisawkward.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
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
            "alertAccepted" to false
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
    fun checkUserOnDate(callback: (Boolean, String) -> Unit) {
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

                        if (startTime != null && endTime != null && date != null) {
                            try {
                                if (isDateHappeningNow(date, startTime, endTime)){
                                    callback(true, doc.id)
                                    return@addOnSuccessListener
                                }
                            }
                            catch (e: Exception) {
                                println("error")
                            }
                        }
                    }
                    callback(false, "")
                }
        } ?: callback(false, "") // If user is null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDates(callback: (List<Any>) -> Unit) {
        val dates = mutableListOf<Map<String, Any>>()

        user?.let {
            val currentUserId = it.uid
            val tasks = mutableListOf<Task<DocumentSnapshot>>()

            FirebaseFirestore.getInstance()
                .collectionGroup("dates")
                .get()
                .addOnSuccessListener { snapshot ->
                    for (doc in snapshot) {
                        val documentPath = doc.reference.path // This gives the full path
                        val pathParts = documentPath.split("/")
                        val dateUserId = pathParts.getOrNull(1) // This should give the user ID from the path

                        if (dateUserId != null && dateUserId != currentUserId) {
                            val userTask = FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(dateUserId)
                                .get()

                            tasks.add(userTask)

                            // Fetch the name of the user who created the date
                            FirebaseFirestore.getInstance()
                                .collection("users")
                                .document(dateUserId)
                                .get()
                                .addOnSuccessListener { userDoc ->
                                    val name = userDoc.getString("name") ?: ""
                                    val phone = userDoc.getString("phone") ?: ""

                                    val date = doc.getString("date")
                                    val startTime = doc.getString("startTime")
                                    val endTime = doc.getString("endTime")

                                    if (
                                        startTime != null &&
                                        endTime != null && date != null &&
                                        isDateHappeningNow(date, startTime, endTime) &&
                                        doc.getBoolean("alertCreated") == true &&
                                        doc.getBoolean("alertAccepted") == false
                                    ) {
                                        val location = doc.getString("location") ?: "N/A"
                                        val modusOperandi = doc.getString("modusOperandi") ?: "N/A"
                                        val additionalDetails = doc.getString("additionalDetails") ?: "N/A"

                                        dates.add(
                                            hashMapOf(
                                                "name" to name,
                                                "location" to location,
                                                "modusOperandi" to modusOperandi,
                                                "phone" to phone,
                                                "additionalDetails" to additionalDetails
                                            )
                                        )
                                    }
                                    // After all users are fetched, trigger callback
                                    if (dates.size == tasks.size) {
                                        callback(dates)
                                    }

                                }
                                .addOnFailureListener { e ->
                                    println("Error fetching user name: $e")
                                }
                        }
                    }
                }
            // If no matching dates, return empty
            if (tasks.isEmpty()) {
                callback(dates)
            }
        } ?: callback(emptyList())
    }

    fun createDateAlert(dateId: String) {
        user?.let {
            val currentUserId = it.uid
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId)
                .collection("dates")
                .document(dateId)
                .update("alertCreated", true)
                .addOnSuccessListener {
                    println("alertCreated updated successfully")
                }
                .addOnFailureListener { e ->
                    println("Error updating alertCreated: $e")
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isDateHappeningNow(date: String, startTime: String, endTime: String) : Boolean {
        val today = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        val timeNow = LocalTime.now()

        val formatter = DateTimeFormatter.ofPattern("hh:mm a") // 12-hour format with AM/PM

        val start = LocalTime.parse(startTime, formatter)
        val end = LocalTime.parse(endTime, formatter)

        return date == today && timeNow.isAfter(start) && timeNow.isBefore(end)
    }
}