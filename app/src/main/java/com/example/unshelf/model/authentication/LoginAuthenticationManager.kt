package com.example.unshelf.model.authentication

import com.example.unshelf.model.entities.Customer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class LoginAuthenticationManager {
    val firebaseAuth = getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun loginUser(
        email: String,
        password: String,
        onCompleteListener: OnCompleteListener<AuthResult?>
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(onCompleteListener)
        firebaseAuth.signInAnonymously()
    }


    fun firebaseAuthWithGoogle(idToken: String, callback: (Boolean, String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign-in success, check if new user
                    val isNewUser = task.result?.additionalUserInfo?.isNewUser ?: false
                    if (isNewUser) {
                        createNewCustomer(task.result?.user, callback)
                    } else {
                        callback(true, "Existing user logged in successfully.")
                    }
                } else {
                    callback(false, "Firebase Auth failed: ${task.exception?.localizedMessage}")
                }
            }
    }

    private fun createNewCustomer(firebaseUser: FirebaseUser?, callback: (Boolean, String) -> Unit) {
        firebaseUser?.let { user ->
            // Create a new Customer object with available and default fields
            val newCustomer = Customer(
                customerID = user.uid,
                email = user.email ?: "", // Email from Google account
                password = "", // Password is not available from Google Sign-In
                phoneNumber = 0L, // Placeholder for phone number
                fullName = user.displayName ?: "Unknown", // Full name from Google account
                address = "" // Placeholder for address
            )

            // Save the new Customer to Firestore
            firestore.collection("customers").document(user.uid).set(newCustomer)
                .addOnSuccessListener {
                    callback(true, "New user account created successfully.")
                }
                .addOnFailureListener { e ->
                    callback(false, "Failed to create new user: ${e.localizedMessage}")
                }
        } ?: callback(false, "Firebase user data is null.")
    }


}


