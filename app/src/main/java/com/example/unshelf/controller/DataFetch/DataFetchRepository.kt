package com.example.unshelf.controller.DataFetch
import androidx.compose.runtime.mutableStateOf
import com.example.unshelf.model.entities.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DataFetchRepository {

    private val db = FirebaseFirestore.getInstance()

    val products = mutableStateOf<List<Product>>(emptyList())

    val isLoading = mutableStateOf(true)

    private val listenerRegistration = db.collection("products")
        .addSnapshotListener { value, error ->
            error?.let {
                isLoading.value = false
                return@addSnapshotListener
            }
            value?.let {
                products.value = it.toObjects(Product::class.java)
                isLoading.value = false
            }
        }

    fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = db.collection("products").get().await()

                // Switch to the main dispatcher to update the Compose state
                CoroutineScope(Dispatchers.Main).launch {
                    products.value = result.toObjects(Product::class.java)
                    isLoading.value = false
                }
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

    fun clearListener() {
        listenerRegistration.remove()
    }
}
