package com.example.unshelf.controller.DataFetch
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.unshelf.model.entities.Product
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object DataFetchRepository {

    private val db = FirebaseFirestore.getInstance()

    val products = mutableStateOf<List<Product>>(emptyList())

    val isLoading = mutableStateOf(true)

    fun fetchData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = db.collection("products").get().await()
                products.value = result.toObjects(Product::class.java)
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
            }
        }
    }

}
