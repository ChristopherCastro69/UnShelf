package com.example.unshelf.controller.DataFetch

import androidx.compose.runtime.mutableStateOf
import com.example.unshelf.model.entities.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DataFetchRepository {

    private val db = FirebaseFirestore.getInstance()

    val products = mutableStateOf<List<Product>>(emptyList())
    val mappedProducts = mutableStateOf<Map<String, Product>>(emptyMap())

    var isLoading = mutableStateOf(true)

    fun fetchData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                db.collection("products")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            isLoading.value = false
                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val productList = snapshot.toObjects(Product::class.java)
                            products.value = productList

                            val productMap = mutableMapOf<String, Product>()
                            for (product in productList) {
                                productMap[product.productID] = product
                            }
                            mappedProducts.value = productMap

                            isLoading.value = false
                        }
                    }
            } catch (e: Exception) {
                // Handle exception
                isLoading.value = false
            }
        }
    }
}
