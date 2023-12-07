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

    val totalPrice: Double
        get() {
            var sum = 0.0
            for (prod in products) {
                sum += prod.marketPrice + prod.marketPrice * prod.discount
            }
            return sum
        }
}