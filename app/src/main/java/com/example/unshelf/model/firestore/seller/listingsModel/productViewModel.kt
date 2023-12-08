package com.example.unshelf.model.firestore.seller.listingsModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Product
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId
import com.example.unshelf.view.SellerBottomNav.screens.listings.sellerID
import com.example.unshelf.view.SellerBottomNav.screens.listings.sellerId
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products = _products.asStateFlow()

    fun fetchProductsForSeller(sellerId: String, storeId: String) {
        val db = Firebase.firestore
        db.collection("products")
            .whereEqualTo("sellerID", sellerId) // Assuming you store 'sellerId' in each product document
            .get()
            .addOnSuccessListener { documents ->
                val productList = documents.map { document ->
                    val id = document.id // Product ID
                    val fetchedSellerId = document.getString("sellerID") ?: "Unknown" // Get sellerId from the documen
                    val seller = sellerId
                    val store = storeId
                    Log.d("ProductViewModel", "Product ID: $id, Seller ID: $fetchedSellerId")



                    val categories = document.get("categories") as? List<String> ?: listOf("Unknown")
                    val description = document.getString("description") ?: "Unknown"
                     val discount = document.getLong("discount") ?: 0L
                    val expirationDate = document.getString("expirationDate") ?: "0/00/0000"
                    // val gallery = document.getString("gallery") ?: ""  // Assuming gallery is a single String
                    // val hashtags = document.get("hashtags") as? List<String> ?: listOf()
                    val name = document.getString("productName") ?: "Unknown"
                    val quantity = document.getLong("quantity")?.toInt() ?: 0
                    val price = document.getDouble("price")?.toLong() ?: 0L
                    val sellingPrice = document.getDouble("sellingPrice")?.toLong() ?: 0L
                    val isActive = document.getBoolean("isActive") ?: false  // Use getBoolean and provide a default value
                    val thumbnailUri = document.getString("thumbnail") ?: ""

                    Product(id, seller, store, name, quantity, price, sellingPrice, discount, categories, thumbnailUri, description,  expirationDate, isActive)
                }
                _products.value = productList
            }
            .addOnFailureListener { exception ->
                Log.w("ProductViewModel", "Error getting documents: ", exception)
            }
    }


    fun deleteProduct(sellerId: String, storeId: String, productId: String, context: Context) {
        val db = Firebase.firestore
        db.collection("products").document(productId)
            .delete()
            .addOnSuccessListener {
                Log.d("ProductViewModel", "Product successfully deleted: $productId")
                Toast.makeText(context, "Product successfully deleted", Toast.LENGTH_SHORT).show()
                fetchProductsForSeller(sellerId, storeId) // Refresh the product list // You might need to pass the sellerId here
            }
            .addOnFailureListener { e ->
                Log.w("ProductViewModel", "Error deleting product: $productId", e)
                Toast.makeText(context, "Error deleting product", Toast.LENGTH_SHORT).show()
            }
    }





}