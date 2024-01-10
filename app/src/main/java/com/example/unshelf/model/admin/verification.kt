package com.example.unshelf.model.admin

import android.content.Context
import android.content.Intent
import com.example.unshelf.view.authentication.Customer_Login
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

    val firestore = Firebase.firestore
    val batch = firestore.batch()

    // Update the "adminVerified" field in the "sellers" collection
    val sellersRef = firestore.collection("sellers").whereEqualTo("sellerID", userId)
    val sellersQuerySnapshot = sellersRef.get().await()

    if (!sellersQuerySnapshot.isEmpty) {
        val sellerDocumentSnapshot = sellersQuerySnapshot.documents[0]
        val sellerReference = sellerDocumentSnapshot.reference

        batch.update(sellerReference, "adminVerified", "Pending")
    }

    // Update the "verified" field in the "stores" collection
    val storesRef = firestore.collection("stores").whereEqualTo("sellerID", userId)
    val storesQuerySnapshot = storesRef.get().await()

    if (!storesQuerySnapshot.isEmpty) {
        val storeDocumentSnapshots = storesQuerySnapshot.documents

        for (storeDocumentSnapshot in storeDocumentSnapshots) {
            val storeReference = storeDocumentSnapshot.reference
            batch.update(storeReference, "verified", "Pending")
        }
    }

    try {
        // Commit the batched write
        batch.commit().await()

        // Show a success message (you can replace this with your own UI logic)
        println("Verification sent successfully!")
    } catch (e: Exception) {
        // Handle exceptions (e.g., FirebaseFirestoreException)
        println("Failed to send verification: ${e.message}")
    }
}

suspend fun rejectVerificationToAdmin() {
    val userId = Firebase.auth.currentUser?.uid ?: return

    val firestore = Firebase.firestore
    val batch = firestore.batch()

    // Update the "adminVerified" field in the "sellers" collection
    val sellersRef = firestore.collection("sellers").whereEqualTo("sellerID", userId)
    val sellersQuerySnapshot = sellersRef.get().await()

    if (!sellersQuerySnapshot.isEmpty) {
        val sellerDocumentSnapshot = sellersQuerySnapshot.documents[0]
        val sellerReference = sellerDocumentSnapshot.reference

        batch.update(sellerReference, "adminVerified", "Rejected")
    }

    // Update the "verified" field in the "stores" collection
    val storesRef = firestore.collection("stores").whereEqualTo("sellerID", userId)
    val storesQuerySnapshot = storesRef.get().await()

    if (!storesQuerySnapshot.isEmpty) {
        val storeDocumentSnapshots = storesQuerySnapshot.documents

        for (storeDocumentSnapshot in storeDocumentSnapshots) {
            val storeReference = storeDocumentSnapshot.reference
            batch.update(storeReference, "verified", "Rejected")
        }
    }

    try {
        // Commit the batched write
        batch.commit().await()

        // Show a success message (you can replace this with your own UI logic)
        println("Verification rejected successfully!")
    } catch (e: Exception) {
        // Handle exceptions (e.g., FirebaseFirestoreException)
        println("Failed to reject verification: ${e.message}")
    }
}


suspend fun banSeller() {
    val userId = Firebase.auth.currentUser?.uid ?: return

    val firestore = Firebase.firestore
    val batch = firestore.batch()

    // Update the "adminVerified" field in the "sellers" collection
    val sellersRef = firestore.collection("sellers").whereEqualTo("sellerID", userId)
    val sellersQuerySnapshot = sellersRef.get().await()

    if (!sellersQuerySnapshot.isEmpty) {
        val sellerDocumentSnapshot = sellersQuerySnapshot.documents[0]
        val sellerReference = sellerDocumentSnapshot.reference

        batch.update(sellerReference, "adminVerified", "Banned")
    }

    // Update the "verified" field in the "stores" collection
    val storesRef = firestore.collection("stores").whereEqualTo("sellerID", userId)
    val storesQuerySnapshot = storesRef.get().await()

    if (!storesQuerySnapshot.isEmpty) {
        val storeDocumentSnapshots = storesQuerySnapshot.documents

        for (storeDocumentSnapshot in storeDocumentSnapshots) {
            val storeReference = storeDocumentSnapshot.reference
            batch.update(storeReference, "verified", "Banned")
        }
    }

    try {
        // Commit the batched write
        batch.commit().await()

        // Show a success message (you can replace this with your own UI logic)
        println("Seller banned successfully!")
    } catch (e: Exception) {
        // Handle exceptions (e.g., FirebaseFirestoreException)
        println("Failed to ban seller: ${e.message}")
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

suspend fun logoutBuyer(context: Context) {
    try {
        FirebaseAuth.getInstance().signOut()
        // Add any additional cleanup or logic you need after logout

        // Navigate to the SellerLoginView
        val intent = Intent(context, Customer_Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        println("Logout successful!")
    } catch (e: Exception) {
        // Handle exceptions (e.g., FirebaseAuthException)
        println("Failed to logout: ${e.message}")
    }
}

