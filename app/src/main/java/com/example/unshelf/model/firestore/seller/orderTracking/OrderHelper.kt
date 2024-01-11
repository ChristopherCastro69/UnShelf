package com.example.unshelf.model.firestore.seller.orderTracking

import android.util.Log
import com.example.unshelf.model.entities.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class OrderHelper {
}

val sellerIDs: MutableMap<String, String> = mutableMapOf()
var OrderTracking_Orders: MutableList<Order> = mutableListOf()
suspend fun getOrders(): List<Order> {
    return withContext(Dispatchers.IO) {
        try {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            if (user != null) {
                Log.d("USERRR", "The user ID is ${user.uid}")

                val db = Firebase.firestore
                val query = db.collection("orders")
                    .whereEqualTo("customerID", user.uid)
                    .get()
                    .await()

                val orders = query.documents.map { document ->
                    val order = document.toObject(Order::class.java)
                    sellerIDs[order?.sellerID ?: ""] = ""
                    order?.copy() ?: Order()
                }
                OrderTracking_Orders = orders.toMutableList()
                Log.d("SELLERID", "SELLER $sellerIDs")
                return@withContext orders
            } else {
                throw IllegalStateException("User not authenticated")
            }
        } catch (exception: Exception) {
            Log.d("Database Fetch", "Error getting documents: ", exception)
            return@withContext emptyList()
        }
    }
}

suspend fun getSellerName() {
    try {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            Log.d("USERRR", "The user ID is ${user.uid}")

            val db = Firebase.firestore
            val query = db.collection("sellers")
                .get()
                .await()

            val sellerDocuments = query.documents
            sellerDocuments.forEach { sellerDocument ->
                val sellerID = sellerDocument.id
                val storeName = sellerDocument.getString("storeName")

                if (sellerIDs.containsKey(sellerID)) {
                    sellerIDs[sellerID] = storeName ?: ""
                }
            }

            Log.d("SELLERID", "SELLER $sellerIDs")
        } else {
            throw IllegalStateException("User not authenticated")
        }
    } catch (exception: Exception) {
        Log.d("Database Fetch", "Error getting documents: ", exception)

    }
}


