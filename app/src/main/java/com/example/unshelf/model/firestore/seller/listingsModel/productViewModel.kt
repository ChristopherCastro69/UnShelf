package com.example.unshelf.model.firestore.seller.listingsModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.ProductWithID
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<ProductWithID>>(listOf())
    val products = _products.asStateFlow()

    fun fetchProductsForSeller(sellerId: String, storeId: String) {
        val db = Firebase.firestore
        db.collection("sellers").document(sellerId)
            .collection("store").document(storeId)
            .collection("products")
            .get()
            .addOnSuccessListener { documents ->
                val productList = documents.map { document ->
                    // Log the product ID
                    Log.d("ProductViewModel", "Product ID: ${document.id}")

                    val id = document.id
                    val categories = document.get("categories") as? List<String> ?: listOf("Unknown")
                    val description = document.getString("description") ?: "Unknown"
                    val discount = document.getLong("discount") ?: 0L
                    val expirationDate = document.getString("expirationDate") ?: "0/00/0000"
                    val gallery = document.getString("gallery") ?: ""  // Assuming gallery is a single String
                    val hashtags = document.get("hashtags") as? List<String> ?: listOf()
                    val name = document.getString("productName") ?: "Unknown"
                    val quantity = document.getLong("quantity")?.toInt() ?: 0
                    val price = document.getDouble("marketPrice")?.toLong() ?: 0L
                    val thumbnailUri = document.getString("thumbnail") ?: ""
                    ProductWithID(id, name, categories, thumbnailUri, gallery, description, price, hashtags, expirationDate, discount, quantity)
                }
                _products.value = productList
            }
            .addOnFailureListener { exception ->
                Log.w("ProductViewModel", "Error getting documents: ", exception)
            }
    }

    fun deleteProduct(sellerId: String, storeId: String, productId: String, context: Context) {
        val db = Firebase.firestore
        db.collection("sellers").document(sellerId)
            .collection("store").document(storeId)
            .collection("products").document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d("ProductViewModel", "Product successfully deleted: $productId")
                Toast.makeText(context, "Product successfully deleted", Toast.LENGTH_SHORT).show()
                fetchProductsForSeller(sellerId, storeId) // Refresh the product list
            }
            .addOnFailureListener { e ->
                Log.w("ProductViewModel", "Error deleting product: $productId", e)
                Toast.makeText(context, "Error deleting product", Toast.LENGTH_SHORT).show()
            }
    }




}