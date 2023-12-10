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
        3.0,
        30.0,
        10.0,
        "AAAA",
        listOf("Abc", "bcd"),
        "yoo",
        "sample",
        "12-12-12",
        false
    )

    val prod2 = Product(
        "1",
        "0",
        "000",
        "Another Fruit",
        3,
        4.5,
        40.0,
        15.0,
        "BBBB",
        listOf("Xyz", "def"),
        "xyz",
        "another sample",
        "11-11-11",
        true
    )

    val prod3 = Product(
        "2",
        "1",
        "001",
        "Exotic Fruit",
        1,
        5.0,
        50.0,
        20.0,
        "CCCC",
        listOf("Pqr", "ghi"),
        "pqr",
        "exotic sample",
        "10-10-10",
        true
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
