package com.example.unshelf.view.productView

import com.example.unshelf.model.entities.Product

class CartDummyInfo {

}

fun productDummyData(): List<Product> {
    val prod1 = Product(
        "0",
        "0",
        "000",
        "Lucky's Fruit",
        2,
        3,
        30,
        10,
        listOf("Abc", "bcd"),
        "yoo",
        "sample",
        "12-12-12",
        false
    )

    val prod2 = Product(
        "0",
        "0",
        "000",
        "Lucky's Fruit",
        2,
        3,
        30,
        10,
        listOf("Abc", "bcd"),
        "yoo",
        "sample",
        "12-12-12",
        false
    )

    val prod3 = Product(
        "0",
        "1",
        "001",
        "Lucky's Fruit",
        2,
        3,
        30,
        10,
        listOf("Abc", "bcd"),
        "yoo",
        "sample",
        "12-12-12",
        false
    )

    return listOf(prod1, prod2, prod3)
}

fun getStores(): Map<String, List<Product>> {
    val products: List<Product> = productDummyData()
    val storesMap: MutableMap<String, MutableList<Product>> = mutableMapOf()

    products.forEach { product ->
        val storeID = product.storeID
        if (storesMap.containsKey(storeID)) {
            // Store ID already exists, add the product to the existing list
            storesMap[storeID]?.add(product)
        } else {
            // Store ID doesn't exist, create a new list and add the product
            storesMap[storeID] = mutableListOf(product)
        }
    }

    return storesMap
}
