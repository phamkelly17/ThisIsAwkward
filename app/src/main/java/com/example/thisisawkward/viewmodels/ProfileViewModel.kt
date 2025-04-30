package com.example.thisisawkward.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {
    private val auth by lazy { FirebaseAuth.getInstance() }

    val user = auth.currentUser

    fun getProfileInfo(callback: (Int, Int, Int, String, String) -> Unit) {
        user?.let {
            val currentUserId = it.uid
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId)
                .get()
                .addOnSuccessListener { document ->
                    val datesCrashed =  document.getLong("datesCrashed")?.toInt() ?: 0
                    val crashedDates =  document.getLong("crashedDates")?.toInt() ?: 0
                    val totalDates =  document.getLong("totalDates")?.toInt() ?: 0
                    val name =  document.getString("name") ?: ""
                    val dateJoined =  document.getString("dateJoined") ?: ""

                    callback(datesCrashed, crashedDates, totalDates, name, dateJoined)
                    return@addOnSuccessListener
                }
                .addOnFailureListener { e ->
                    println("Error getting stats: $e")
                }
        }
        callback(0,0,0, "", "")
    }

    fun incrementStat(statName: String, increment: Long) {
        user?.let {
            val currentUserId = it.uid
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId)
                .update(statName, FieldValue.increment(increment))
                .addOnSuccessListener {
                    println("$statName updated successfully")
                }
                .addOnFailureListener { e ->
                    println("Error updating $statName: $e")
                }
        }
    }

    fun incrementCrashedDates(userId: String, increment: Long) {
        user?.let {
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(userId)
                .update("crashedDates", FieldValue.increment(increment))
                .addOnSuccessListener {
                    println("crashedDates updated successfully")
                }
                .addOnFailureListener { e ->
                    println("Error updating crashedDates: $e")
                }
        }
    }

    fun updateModusOperandum(modus: String) {
        user?.let {
            val currentUserId = it.uid
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId)
                .update("modusOperandum", modus)
                .addOnSuccessListener {
                    println("modusOperandum updated successfully")
                }
                .addOnFailureListener { e ->
                    println("Error updating modusOperandum: $e")
                }
        }
    }

    fun getModusOperandum(callback: (String) -> Unit) {
        user?.let {
            val currentUserId = it.uid
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId)
                .get()
                .addOnSuccessListener { document ->
                    val modusOperandum =  document.getString("modusOperandum") ?: ""
                    callback(modusOperandum)
                    return@addOnSuccessListener
                }
                .addOnFailureListener { e ->
                    println("Error updating modusOperandum: $e")
                }
        }
        callback("")
    }
}