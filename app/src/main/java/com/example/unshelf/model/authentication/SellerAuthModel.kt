package com.example.unshelf.model.authentication

import android.util.Patterns
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.entities.Seller
import com.example.unshelf.model.entities.Store
import com.example.unshelf.model.entitiesdraft.ProductsDraft
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SellerAuthModel {
    fun createSellerAccountFirebase(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        storeName: String,
        productList: List<Product>,
        rating: Long,
        sellerID : String,
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
                    // Create a Customer object with the provided details
                    val seller = Seller(
                        email,
                        password,
                        phoneNumber,
                        fullName,
                        address,
                        storeName,
                        adminVerified)

                    val store = Store(
                        sellerID,
                        storeName,
                        rating,
                        productList,
                        adminVerified
                    )
                    // Get the current user's UID
                    val uid = firebaseAuth.currentUser!!.uid

                    // Store the customer details in Firestore under the "Customers" collection
                    firestore.collection("sellers").document(uid)
                        .set(seller)
                        .addOnSuccessListener {
                            callback(true, "Account Successfully Created. Check email for verification")
                            firebaseAuth.currentUser!!.sendEmailVerification()
                            firebaseAuth.signOut()
                        }
                        .addOnFailureListener { e ->
                            callback(false, "Failed to store seller details: ${e.localizedMessage}")
                        }
                    // Store the customer details in Firestore under the "Customers" collection
                    firestore.collection("stores").document(uid)
                        .set(store)
                        .addOnSuccessListener {
                            callback(true, "Store Successfully Created. Awaiting approval")
                            firebaseAuth.signOut()
                        }
                        .addOnFailureListener { e ->
                            callback(false, "Failed to store details: ${e.localizedMessage}")
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