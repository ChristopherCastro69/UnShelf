package com.example.unshelf.model.entities


class Store (
    val sellerID : String,
    val storeName : String,
    val rating : Long,
    val products: List<Product>,
    val isVerified : String,
)

