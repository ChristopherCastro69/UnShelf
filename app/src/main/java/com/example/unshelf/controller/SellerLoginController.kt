package com.example.unshelf.controller

// SellerLoginController.kt

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.unshelf.model.authentication.LoginAuthenticationManager
import com.example.unshelf.controller.seller.ui.MainNavigationActivitySeller
import com.example.unshelf.view.authentication.SellerLoginView

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SellerLoginController(private val context: Context, private val view: SellerLoginView, private val model: LoginAuthenticationManager) :
    SellerLoginView.LoginListener {

    fun init() {
        view.setLoginListener(this)
        view.initViews()
    }

    override fun onLoginClicked(email: String, password: String) {
        view.changeInProgress(true)
        model.loginUser(email, password, OnCompleteListener { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
                val firebaseAuth = model.firebaseAuth
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    // Go to the main activity for sellers
                    val intent = Intent(context, MainNavigationActivitySeller::class.java)
                    context.startActivity(intent)
                } else {
                    view.showToast("Email not verified, Please verify your email.")
                    view.changeInProgress(false)
                }
            } else {
                view.showToast(task.exception!!.localizedMessage)
                view.changeInProgress(false)
            }
        })
    }
}


fun fetchUserDetails(onComplete: (String, String) -> Unit) {
    val userId = Firebase.auth.currentUser?.uid ?: return

    // Assuming 'userId' is your 'sellerId'
    val sellerId = userId

    // Query Firestore to get the store ID
    Firebase.firestore.collection("sellers").document(sellerId)
        .collection("store").get()
        .addOnSuccessListener { querySnapshot ->
            // Assuming you need the first store's ID
            val storeId = querySnapshot.documents.firstOrNull()?.id ?: ""
            Log.d("UserDetails", "Seller ID: $sellerId, Store ID: $storeId")

            // Return the seller ID and store ID
            onComplete(sellerId, storeId)
        }
        .addOnFailureListener {
            Log.e("Firestore", "Error fetching store details", it)
        }
}
