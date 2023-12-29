package com.example.unshelf.view.productView

import android.util.Log
import com.example.unshelf.model.entities.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CartDummyInfo {

}

fun productDummyData(): List<Product> {
    val prod1 = Product(
        "0",
        "0",
        "000",
        "UNSHELF",
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
        "UNSHELF",
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
        false
    )

    val prod3 = Product(
        "2",
        "1",
        "001",
        "UNSHELF",
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
        false
    )

    return listOf(prod1, prod2, prod3)
}

fun getTotalAmount() : Double {
    var sum : Double = 0.0
    val products: List<Product> = productDummyData()
    products.forEach { product ->
        sum += product.sellingPrice
     }
    return sum
}

data class productCheckOutQuantity(
    val product: Product,
    var qty: Int = 0,
)

fun fetchUser() : FirebaseUser? {
    val auth = FirebaseAuth.getInstance()
    return auth.currentUser
}

suspend fun updateCart(userId: String?, productId: String?) {
    if (userId != null && productId != null) {
        try {
            val db = FirebaseFirestore.getInstance()
            val cartDocument = db.collection("carts").document(userId)
            cartDocument.update("products", FieldValue.arrayUnion(productId))
                .await()
            Log.d("Firestore", "IT WORKS?")

        } catch (e: Exception) {
             Log.d("Firestore", "Error updating cart data: ${e.message}")
        }
    } else {
         Log.d("Firestore", "User ID or Product ID is null")
    }
}


suspend fun getProductIds(): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            if (user != null) {
                Log.d("USERRR", "The user ID is ${user.uid}")

                val db = Firebase.firestore
                val result = db.collection("carts").document(user.uid).get().await()
                result.data?.get("products") as? List<String> ?: emptyList()
            } else {
                throw IllegalStateException("User not authenticated")
            }
        } catch (exception: Exception) {
            Log.d("Database Fetch", "Error getting documents: ", exception)
            emptyList()
        }
    }
}

suspend fun getProducts(): List<Product> {
    return withContext(Dispatchers.IO) {
        val productIds = getProductIds()
        val productListDatas: MutableList<Product> = mutableListOf()

        try {
            val db = Firebase.firestore
            productIds.forEach { productId ->
                val result = db.collection("products").document(productId).get().await()
                val productData = result.toObject(Product::class.java)
                productData?.let {
                    productListDatas.add(it)
                }
            }
        } catch (exception: Exception) {
            Log.d("Database Fetch", "Error getting documents: ", exception)
        }

        Log.d("Database Fetch", "DATA COR ${productListDatas} ")
        productListDatas
    }
}

fun getStores(products : List<Product>): Map<String, List<Product>> {
    val storesMap: MutableMap<String, MutableList<Product>> = mutableMapOf()

    products.forEach { product ->
        val storeID = product.storeID
        if (storesMap.containsKey(storeID)) {
            storesMap[storeID]?.add(product)
        } else {
            storesMap[storeID] = mutableListOf(product)
        }
    }
    return storesMap
}
