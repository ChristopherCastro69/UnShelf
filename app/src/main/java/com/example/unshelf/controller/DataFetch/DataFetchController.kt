package com.example.unshelf.controller.DataFetch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unshelf.model.entities.ProductDetailsModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object DataFetchController: ViewModel(){
    val db = Firebase.firestore
    var dataFetch = mutableListOf<ProductDetailsModel>()

    init {
        getData()
    }
    public fun getData(): MutableList<ProductDetailsModel> {
        viewModelScope.launch {
            dataFetch = getProducts()
        }
        return dataFetch
    }
    suspend fun getProducts(): MutableList<ProductDetailsModel> {
        var products = mutableListOf<ProductDetailsModel>()
        try {
            val result = db.collection("Product").get().await()
            products = result.toObjects(ProductDetailsModel::class.java)
        } catch (exception: Exception) {
            Log.d("Database Fetch", "Error getting documents: ", exception)
        }
        return products
    }
}
