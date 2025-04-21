package com.example.thisisawkward.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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

    fun signup (
        email: String,
        password: String,
        name: String,
        age: String,
        region: String,
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
                        "region" to region,
                        "phone" to phone
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

}