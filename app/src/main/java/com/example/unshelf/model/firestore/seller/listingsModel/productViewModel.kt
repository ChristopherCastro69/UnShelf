package com.example.unshelf.model.firestore.seller.listingsModel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Product
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products = _products.asStateFlow()

    fun fetchActiveProductsForSeller(sellerId: String, storeId: String) {
        Log.d("ProductViewModel", "Fetching active products for seller: $sellerId, store: $storeId")

        val db = Firebase.firestore
        db.collection("products")
            .whereEqualTo("sellerID", sellerId) // Assuming you store 'sellerId' in each product document
            .whereEqualTo("active", true) // Add this line to filter by isActive status
            .get()
            .addOnSuccessListener { documents ->
                val productList = documents.map { document ->
                    val id = document.id // Product ID
                    val fetchedSellerId = document.getString("sellerID") ?: "Unknown" // Get sellerId from the documen
                    val seller = sellerId
                    val store = storeId
                    Log.d("ProductViewModel", "Product ID: $id, Seller ID: $fetchedSellerId")
                    val storeName = document.getString("storeName") ?: "Unknown"
                    val categories = document.get("categories") as? List<String> ?: listOf("Unknown")
                    val description = document.getString("description") ?: "Unknown"
                    val discount = document.getDouble("discount") ?: 0.00
                    val voucherCode = document.getString("voucherCode") ?: ""
                    val expirationDate = document.getString("expirationDate") ?: "0/00/0000"
                    val name = document.getString("productName") ?: "Unknown"
                    val quantity = document.getLong("quantity")?.toInt() ?: 0
                    val price = document.getDouble("price") ?: 0.00
                    val sellingPrice = document.getDouble("sellingPrice") ?: 0.00
                    val isActive = document.getBoolean("active") ?: false  // Use getBoolean and provide a default value
                    val thumbnailUri = document.getString("thumbnail") ?: ""

                    Product(id, seller, store,storeName, name, quantity, price, sellingPrice, discount, voucherCode, categories, thumbnailUri, description,  expirationDate, isActive)
                }
                _products.value = productList
                Log.d("ProductViewModel", "Product list size: ${productList.size}")
            }
            .addOnFailureListener { exception ->
                Log.w("ProductViewModel", "Error getting documents: ", exception)
            }
    }

    fun fetchInactiveProductsForSeller(sellerId: String, storeId: String) {
        val db = Firebase.firestore

        db.collection("products")
            .whereEqualTo("sellerID", sellerId) // Assuming you store 'sellerId' in each product document
            .whereEqualTo("active", false) // Add this line to filter by isActive status
            .get()
            .addOnSuccessListener { documents ->
                val productList = documents.map { document ->
                    val id = document.id // Product ID
                    val fetchedSellerId = document.getString("sellerID") ?: "Unknown" // Get sellerId from the documen
                    val seller = sellerId
                    val store = storeId
                    Log.d("ProductViewModel", "Product ID: $id, Seller ID: $fetchedSellerId")

                    val storeName = document.getString("storeName") ?: "Unknown"

                    val categories = document.get("categories") as? List<String> ?: listOf("Unknown")
                    val description = document.getString("description") ?: "Unknown"
                    val discount = document.getDouble("discount") ?: 0.00
                    val voucherCode = document.getString("voucherCode") ?: ""
                    val expirationDate = document.getString("expirationDate") ?: "0/00/0000"
                    val name = document.getString("productName") ?: "Unknown"
                    val quantity = document.getLong("quantity")?.toInt() ?: 0
                    val price = document.getDouble("price") ?: 0.00
                    val sellingPrice = document.getDouble("sellingPrice") ?: 0.00
                    val isActive = document.getBoolean("active") ?: false  // Use getBoolean and provide a default value
                    val thumbnailUri = document.getString("thumbnail") ?: ""

                    Product(id, seller, store,storeName, name, quantity, price, sellingPrice, discount, voucherCode, categories, thumbnailUri, description,  expirationDate, isActive)
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
                fetchActiveProductsForSeller(sellerId, storeId) // Refresh the product list // You might need to pass the sellerId here
            }
            .addOnFailureListener { e ->
                Log.w("ProductViewModel", "Error deleting product: $productId", e)
                Toast.makeText(context, "Error deleting product", Toast.LENGTH_SHORT).show()
            }
    }




}