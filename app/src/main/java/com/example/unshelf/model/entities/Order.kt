package com.example.unshelf.model.entities

data class Order(
    val productImageRes: Int,
    val productName: String,
    val orderID: String,
    val orderDate: String,
    val price: Double,
    val status: String
    )
