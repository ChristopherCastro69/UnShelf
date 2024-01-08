package com.example.unshelf.model.admin

import android.content.Context
import android.content.Intent
import com.example.unshelf.view.authentication.SellerLoginView
import com.example.unshelf.view.authentication.Seller_Login
import com.example.unshelf.view.authentication.Seller_Register
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

suspend fun getAdminVerificationStatus(): String {
    val userId = Firebase.auth.currentUser?.uid ?: return ""

    val querySnapshot = Firebase.firestore.collection("sellers")
        .whereEqualTo("sellerID", userId)
        .get()
        .await()  // Assuming you are using kotlinx.coroutines and Firebase's Task.await extension

    if (!querySnapshot.isEmpty) {
        val documentSnapshot: DocumentSnapshot = querySnapshot.documents[0]
        return documentSnapshot.getString("adminVerified") ?: ""
    }

    return ""
}

suspend fun sendVerificationToAdmin() {
    val userId = Firebase.auth.currentUser?.uid ?: return

    val querySnapshot = Firebase.firestore.collection("sellers")
        .whereEqualTo("sellerID", userId)
        .get()
        .await()

    if (!querySnapshot.isEmpty) {
        val documentSnapshot = querySnapshot.documents[0]

        try {
            // Update the adminVerified field to "Pending"
            documentSnapshot.reference.update("adminVerified", "Pending").await()

            // Show a success message (you can replace this with your own UI logic)
            println("Verification sent successfully!")
        } catch (e: Exception) {
            // Handle exceptions (e.g., FirebaseFirestoreException)
            println("Failed to send verification: ${e.message}")
        }
    }
}

suspend fun rejectVerificationToAdmin() {
    val userId = Firebase.auth.currentUser?.uid ?: return

    val querySnapshot = Firebase.firestore.collection("sellers")
        .whereEqualTo("sellerID", userId)
        .get()
        .await()

    if (!querySnapshot.isEmpty) {
        val documentSnapshot = querySnapshot.documents[0]

        try {
            // Update the adminVerified field to "Pending"
            documentSnapshot.reference.update("adminVerified", "Rejected").await()

            // Show a success message (you can replace this with your own UI logic)
            println("Verification sent successfully!")
        } catch (e: Exception) {
            // Handle exceptions (e.g., FirebaseFirestoreException)
            println("Failed to send verification: ${e.message}")
        }
    }
}
suspend fun logoutUser(context: Context) {
    try {
        FirebaseAuth.getInstance().signOut()
        // Add any additional cleanup or logic you need after logout

        // Navigate to the SellerLoginView
        val intent = Intent(context, Seller_Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        println("Logout successful!")
    } catch (e: Exception) {
        // Handle exceptions (e.g., FirebaseAuthException)
        println("Failed to logout: ${e.message}")
    }
}

