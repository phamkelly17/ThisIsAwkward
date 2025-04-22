package com.example.thisisawkward.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class AuthViewModel : ViewModel() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val db by lazy { FirebaseFirestore.getInstance() }

    fun login (email: String, password: String, navController: NavController, errorMessage: MutableState<String>) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("homeLoadingScreen")
                } else {
                    errorMessage.value = task.exception?.localizedMessage ?: "An unknown error occurred"
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun signup (
        email: String,
        password: String,
        name: String,
        age: String,
        phone: String,
        navController: NavController,
        errorMessage: MutableState<String>
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val userData = hashMapOf(
                        "name" to name,
                        "age" to age,
                        "phone" to phone,
                        "dateJoined" to formatDateToWords(),
                        "datesCrashed" to 0,
                        "crashedDates" to 0,
                        "totalDates" to 0,
                        "modusOperandum" to ""
                    )

                    user?.let {
                        db.collection("users").document(it.uid).set(userData)
                            .addOnFailureListener { errorMessage.value = it.localizedMessage }
                    }

                    navController.navigate("homeLoadingScreen")
                } else {
                    errorMessage.value = task.exception?.localizedMessage ?: "An unknown error occurred"
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDateToWords(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")
        return today.format(formatter)
    }

}