package com.example.unshelf.model.entities

import com.google.firebase.database.PropertyName

data class ProductDetailsModel (
    @PropertyName("storeName") val storeName: String = "",
    @PropertyName("productName") val productName: String = "",
    @PropertyName("description") val description: String? = "",
    @PropertyName("quantity") val quantity: Long = 0,
    @PropertyName("category") val category: String? = "",
    @PropertyName("price") val price: Double = 0.0,
    @PropertyName("expirationDate") val expirationDate: String = "",
    @PropertyName("imageUrl") val imageUrl: String? = ""
)