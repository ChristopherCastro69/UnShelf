package com.example.unshelf.model.entities


class Store ( // please add methods (example: getters or setters)
// (if user can modify something, add setter)
// (use getters for a summary of details, returning an object or string)
    val sellerID : String,
    val storeName : String,
    val rating : Long,
    val products: List<Product>,
    val isVerified : String,
)

