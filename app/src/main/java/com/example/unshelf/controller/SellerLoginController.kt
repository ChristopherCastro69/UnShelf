package com.example.unshelf.controller

// SellerLoginController.kt

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.unshelf.model.authentication.LoginAuthenticationManager
import com.example.unshelf.view.adminApproval.VerificationS
import com.example.unshelf.view.authentication.SellerLoginView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
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
                // Login is successful
                val firebaseAuth = model.firebaseAuth
                if (firebaseAuth.currentUser!!.isEmailVerified) {
                    // Check if the user is registered as a customer in Firestore
                    checkIfRegisteredAsSeller(firebaseAuth.currentUser?.uid ?: "")
                } else {
                    view.showToast("Email not verified, Please verify your email.")
                    view.changeInProgress(false)
                }
            } else {
                view.showToast("Invalid email or password")
                view.changeInProgress(false)
            }
        })
    }

    private fun checkIfRegisteredAsSeller(userId: String) {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("sellers").document(userId).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // User is registered as a customer, proceed to MainNavigationActivityBuyer
                    val intent = Intent(context, VerificationS::class.java)
                    context.startActivity(intent)
                } else {
                    // User is not registered as a customer
                    view.showToast("You are not registered as a seller. Please register.")
                    // Redirect to registration screen (Customer_Register class)
                    // Replace the next line with the code to navigate to the registration screen
                    // val intent = Intent(context, Customer_Register::class.java)
                    // context.startActivity(intent)
                    view.changeInProgress(false)
                }
            }
            .addOnFailureListener { e ->
                view.showToast("Failed to check customer registration: ${e.localizedMessage}")
                view.changeInProgress(false)
            }
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
