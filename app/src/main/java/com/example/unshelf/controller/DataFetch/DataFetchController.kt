package com.example.unshelf.controller.DataFetch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.entities.ProductDetailsModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object DataFetchController: ViewModel(){
    val db = Firebase.firestore
    var dataFetch = mutableListOf<Product>()

    init {
        getData()
    }
    public fun getData(): MutableList<Product> {
        viewModelScope.launch {
            dataFetch = getProducts()
            Log.d("Controller Fetch", "${dataFetch.size}")
        }
        return dataFetch
    }
    suspend fun getProducts(): MutableList<Product> {
        var products = mutableListOf<Product>()
        try {
            val result = db.collection("products").get().await()
            products = result.toObjects(Product::class.java)
        } catch (exception: Exception) {
            Log.d("Database Fetch", "Error getting documents: ", exception)
        }
        return products
    }
}
