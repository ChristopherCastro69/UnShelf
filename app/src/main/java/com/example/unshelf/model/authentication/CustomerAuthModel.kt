package com.example.unshelf.model.authentication

import android.util.Patterns
import com.example.unshelf.model.entities.Cart
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.model.entities.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomerAuthModel() {


    fun createCustomerAccountFirebase(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        callback: (Boolean, String?) -> Unit
    ) {
        // Initialize Firebase
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        // Firebase authentication logic
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Create a Customer object with the provided details
                    val customer = Customer(
                        email,
                        password,
                        phoneNumber,
                        fullName,
                        address
                    )

                    // Get the current user's UID
                    val uid = firebaseAuth.currentUser!!.uid

                    // Store the customer details in Firestore under the "Customers" collection
                    firestore.collection("customers").document(uid)
                        .set(customer)
                        .addOnSuccessListener {
                            // Create a Cart object (you can customize this based on your Cart class)
                            val cart = Cart(uid, arrayListOf<Product>())

                            // Store the cart details in Firestore under the "Carts" collection
                            firestore.collection("carts").document(uid)
                                .set(cart)
                                .addOnSuccessListener {
                                    callback(true, "Account Successfully Created. Check email for verification")
                                    firebaseAuth.currentUser!!.sendEmailVerification()
                                    firebaseAuth.signOut()
                                }
                                .addOnFailureListener { e ->
                                    callback(false, "Failed to store cart details: ${e.localizedMessage}")
                                }
                        }
                        .addOnFailureListener { e ->
                            callback(false, "Failed to store customer details: ${e.localizedMessage}")
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

    class FirestoreDataManager {

        private val db = FirebaseFirestore.getInstance()

        fun fetchDataFromFirestore(collectionName: String, callback: (Boolean, List<Map<String, Any>>?) -> Unit) {
            val dataList = mutableListOf<Map<String, Any>>()

            db.collection(collectionName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        // Convert each document to a map and add it to the list
                        val dataMap = document.data
                        dataList.add(dataMap)
                    }
                    callback(true, dataList)
                }
                .addOnFailureListener { e ->
                    callback(false, null)
                }
        }
    }
}
