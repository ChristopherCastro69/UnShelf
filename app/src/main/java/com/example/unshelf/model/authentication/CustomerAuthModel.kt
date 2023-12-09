package com.example.unshelf.model.authentication

import android.util.Patterns
import com.example.unshelf.model.entities.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CustomerAuthModel() {


    fun createCustomerAccountFirebase(
        email: String,
        password: String,
        phoneNumber: Long,
        fullName: String,
        address: String,
        callback: (Boolean, String?) -> Unit)
    {
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get the current user's UID, which will be used as the customerID
                    val uid = firebaseAuth.currentUser!!.uid

                    // Create a Customer object with the UID as the customerID
                    val customer = Customer(
                        uid, // Use Firebase Auth UID as the customerID
                        email,
                        password,
                        phoneNumber,
                        fullName,
                        address)

                    // Store the customer details in Firestore under the "customers" collection
                    firestore.collection("customers").document(uid).set(customer)
                        .addOnSuccessListener {
                            callback(true, "Account Successfully Created. Check email for verification")
                            firebaseAuth.currentUser!!.sendEmailVerification()
                            firebaseAuth.signOut()
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


}
