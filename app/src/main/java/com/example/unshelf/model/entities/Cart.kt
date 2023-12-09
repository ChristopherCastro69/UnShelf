package com.example.unshelf.model.entities

class Cart(
    private val customerId: String,
    private val products: ArrayList<Product>
) {
    fun addProduct(productItem: Product) {
        products.add(productItem)
    }

    fun getCustomerId(): String {
        return customerId
    }

}