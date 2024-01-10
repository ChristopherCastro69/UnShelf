package com.example.unshelf.model.authentication

import android.util.Patterns
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.entities.Seller
import com.example.unshelf.model.entities.Store
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
        rating: Double,
        followers: Int,
        adminVerified: String,
        callback: (Boolean, String?) -> Unit)
    {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser!!.uid
                    val seller = Seller(uid, email, password, phoneNumber, fullName, address, storeName, adminVerified)

                    val sellerDocument = firestore.collection("sellers").document(uid)
                    sellerDocument.set(seller)
                        .addOnSuccessListener {
                            // Create a store in the 'stores' collection with an auto-generated ID
                            val newStore = Store("", uid, address, rating, followers, adminVerified, storeName, fullName, "")
                            val storeDocument = firestore.collection("stores").document() // Firestore generates the ID

                            storeDocument.set(newStore)
                                .addOnSuccessListener {
                                    // Set the storeID to the auto-generated ID
                                    newStore.storeID = storeDocument.id
                                    // Update the newStore object in Firestore with the set storeID
                                    storeDocument.set(newStore)
                                        .addOnSuccessListener {
                                            callback(true, "Seller Account and Store Successfully Created. Store ID: ${newStore.storeID}")
                                            firebaseAuth.currentUser!!.sendEmailVerification()
                                            firebaseAuth.signOut()
                                        }
                                        .addOnFailureListener { e ->
                                            callback(false, "Failed to update store with storeID: ${e.localizedMessage}")
                                        }
                                }
                                .addOnFailureListener { e ->
                                    callback(false, "Failed to create store: ${e.localizedMessage}")
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