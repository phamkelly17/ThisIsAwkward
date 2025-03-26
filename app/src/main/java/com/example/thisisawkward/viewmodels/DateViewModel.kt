package com.example.thisisawkward.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DateViewModel : ViewModel() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    val user = auth.currentUser

    fun createDate(time: MutableState<String>,
                   date: MutableState<String>,
                   location: MutableState<String>,
                   modusOperandi: MutableState<String>,
                   additionalDetails: MutableState<String>,
                   errorMessage: MutableState<String>
    ) {
        val dateData = hashMapOf(
            "time" to time.value,
            "date" to date.value,
            "location" to location.value,
            "modusOperandi" to modusOperandi.value,
            "additionalDetails" to additionalDetails.value
        )

        user?.let {
            db.collection("users")
                .document(it.uid)
                .collection("dates")
                .add(dateData)
                .addOnSuccessListener {
                    time.value = ""
                    date.value = ""
                    location.value = ""
                    modusOperandi.value = ""
                    additionalDetails.value = ""
                }
                .addOnFailureListener { errorMessage.value = it.localizedMessage }
        }
    }
}