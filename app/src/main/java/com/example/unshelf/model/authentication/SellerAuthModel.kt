package com.example.unshelf.model.authentication

import android.util.Patterns
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.entities.Seller
import com.example.unshelf.model.entities.Store
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SellerAuthModel { // This is supposed to be in the controller
    fun createSellerAccountFirebase(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        storeName: String,
        rating: Long,
        followers:Int,
        adminVerified: String,

        callback: (Boolean, String?) -> Unit)
    {
        // Initialize Firebase
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        // Firebase authentication logic
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Create a Seller object with the provided details
                    val seller = Seller(
                        email,
                        password,
                        phoneNumber,
                        fullName,
                        address,
                        storeName,
                        adminVerified)

                    val store = Store(
                        address,
                        rating,
                        followers,
                        adminVerified)

                    // Get the current user's UID
                    val uid = firebaseAuth.currentUser!!.uid

                    // Store the seller details in Firestore under the "sellers" collection
                    val sellerDocument = firestore.collection("sellers").document(uid)
                    sellerDocument.set(seller)
                        .addOnSuccessListener {
                            // Create a store with auto-generated ID in the stores sub-collection
                            sellerDocument.collection("store").add(store)
                                .addOnSuccessListener { documentReference ->
                                    // The store is successfully created, and Firestore has generated an ID
                                    callback(true, "Seller Account and Store Successfully Created. Check email for verification. Store ID: ${documentReference.id}")
                                    firebaseAuth.currentUser!!.sendEmailVerification()
                                    firebaseAuth.signOut()
                                }
                                .addOnFailureListener { e ->
                                    callback(false, "Failed to store store details: ${e.localizedMessage}")
                                }
                        }
                        .addOnFailureListener { e ->
                            callback(false, "Failed to store seller details: ${e.localizedMessage}")
                        }
                } else {
                    callback(false, task.exception?.localizedMessage ?: "Unknown error")
                }
            }



    }

    fun validateData(email: String?, password: String, confirmPassword: String?): Boolean {
        // Validate the data input by the user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        if (password.length < 6) {
            return false
        }
        return password == confirmPassword
    }
}