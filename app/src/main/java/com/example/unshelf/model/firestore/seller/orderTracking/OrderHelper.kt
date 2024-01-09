package com.example.unshelf.model.firestore.seller.orderTracking

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class OrderHelper {
}

suspend fun getOrders(): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            if (user != null) {
                Log.d("USERRR", "The user ID is ${user.uid}")

                val db = Firebase.firestore
                val result = db.collection("carts").document(user.uid).get().await()
                result.data?.get("products") as? List<String> ?: emptyList()
            } else {
                throw IllegalStateException("User not authenticated")
            }
        } catch (exception: Exception) {
            Log.d("Database Fetch", "Error getting documents: ", exception)
            emptyList()
        }
    }
}