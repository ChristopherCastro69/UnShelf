package com.example.unshelf.model.entities

class ProductDetailsModel (
    val storeName: String,
    val productName: String,
    val description: String?,
    val quantity: Long,
    val category: String?,
    val price: Double,
    val expirationDate: String,
    val imageUrl: String?
)