package com.example.unshelf.model.firestore.seller.StoreProfileModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.entities.Store
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//class StoreProfileModel : ViewModel(){
//    private val storeList = MutableStateFlow<List<Store>>(listOf())
//    val store = storeList.asStateFlow()
//
//    fun fetchStoreDetails(sellerId: String, storeId: String){
//        val db = Firebase.firestore
//        db.collection("stores")
//            .whereEqualTo("autoID", storeId) // Assuming you store 'sellerId' in each product document
//            .get()
//            .addOnSuccessListener { documents ->
//                val storeDetails = documents.map { document ->
//                    val id = document.id // Store ID
//                    val seller = sellerId
//                    val address = document.getString("address") ?: "Cebu City"
//                    val rating = document.getDouble("rating") ?: 0.00
//                    val followers = document.getLong("followers")?.toInt() ?: 0
//                    val isVerified = document.getString("verified") ?: "Approved"
//                    val storeName = document.getString("storeName") ?: "Unknown"
//                    val sellerName = document.getString("sellerName") ?: "Unknown"
//                    val thumbnail = document.getString("thumbnail") ?: ""
//
//                 Store(id, seller, address, rating, followers, isVerified, storeName, sellerName, thumbnail)
//                }
//                storeList.value = storeDetails
//            }
//            .addOnFailureListener { exception ->
//                Log.w("StoreViewModel", "Error getting documents: ", exception)
//            }
//    }
//}


fun fetchStoreDetails(storeId: String, onSuccess: (Store) -> Unit, onFailure: (Exception) -> Unit) {
    Firebase.firestore.collection("stores").document(storeId)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                try {
                    // Assuming 'Product' has a constructor that matches the document fields
                    val store = Store(
                        storeID = document.id,
                        sellerID = document.getString("sellerId") ?: "",
                        address = document.getString("address") ?: "",
                        rating = document.getDouble("rating") ?: 1.0,
                        followers = document.getDouble("followers")?.toInt() ?: 0,
                        isVerified = document.getString("verified") ?: "Approved",
                        storeName = document.getString("storeName") ?: "",
                        sellerName = document.getString("sellerName") ?: "",
                        thumbnail = document.getString("thumbnail") ?: "",
                       )
                    onSuccess(store)
                } catch (e: Exception) {
                    onFailure(e)
                }
            }
        }
        .addOnFailureListener(onFailure)


}

