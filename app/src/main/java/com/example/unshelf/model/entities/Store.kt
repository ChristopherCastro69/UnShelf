package com.example.unshelf.model.entities

import com.example.unshelf.model.entitiesdraft.ProductsDraft

class Store (
    val sellerID : String,
    val storeName : String,
    val rating : Long,
    val products: List<Product>,
    val isVerified : String,
)

