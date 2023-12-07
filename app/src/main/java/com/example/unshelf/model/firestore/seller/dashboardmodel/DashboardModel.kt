package com.example.unshelf.model.firestore.seller.dashboardmodel

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.model.entities.Product
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.productAdditionSuccess
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

fun fetchUserDetails(onComplete: (String, String) -> Unit) {
    val userId = Firebase.auth.currentUser?.uid ?: return

    // Assuming 'userId' is your 'sellerId'
    val sellerId = userId

    // Query Firestore to get the store ID
    Firebase.firestore.collection("sellers").document(sellerId)
        .collection("store").get()
        .addOnSuccessListener { querySnapshot ->
            // Assuming you need the first store's ID
            val storeId = querySnapshot.documents.firstOrNull()?.id ?: ""
            Log.d("UserDetails", "Seller ID: $sellerId, Store ID: $storeId")

            // Return the seller ID and store ID
            onComplete(sellerId, storeId)
        }
        .addOnFailureListener {
            Log.e("Firestore", "Error fetching store details", it)
        }
}

fun saveProductToFirestore(sellerId: String, storeId: String, product: Product) {
    Log.d("Firestore", "Adding new product")
    val db = Firebase.firestore

    // Create a new document reference without specifying the ID for a new product
    val newProductRef = db.collection("sellers").document(sellerId)
        .collection("store").document(storeId)
        .collection("products").document() // Firestore generates a new ID

    newProductRef.set(product)
        .addOnSuccessListener {
            Log.d("Firestore", "New product added successfully")
            productAdditionSuccess.value = true
        }
        .addOnFailureListener { e ->
            Log.e("Firestore", "Error adding new product", e)
            productAdditionSuccess.value = false
        }
}

fun updateProductToFirestore(sellerId: String, storeId: String, product: Product, productId: String) {
    Log.d("Firestore", "Updating product. Product ID: $productId")
    val db = Firebase.firestore

    val productRef = db.collection("sellers").document(sellerId)
        .collection("store").document(storeId)
        .collection("products").document(productId)

    productRef.update(
        mapOf(
            "productName" to product.productName,
            "categories" to product.categories,
            "thumbnail" to product.thumbnail,
            "gallery" to product.gallery,
            "description" to product.description,
            "marketPrice" to product.marketPrice,
            "hashtags" to product.hashtags,
            "expirationDate" to product.expirationDate,
            "discount" to product.discount,
            "quantity" to product.quantity
        )
    ).addOnSuccessListener {
        Log.d("Firestore", "Product updated successfully")
        productAdditionSuccess.value = true
    }.addOnFailureListener { e ->
        Log.e("Firestore", "Error updating product", e)
        productAdditionSuccess.value = false
    }
}


@Composable
fun DisplayImage(imageUri: Uri) {
    val painter = rememberAsyncImagePainter(model = imageUri)
    Image(
        painter = painter,
        contentDescription = "Loaded Image",
        modifier = Modifier.size(100.dp) // Adjust size as needed
    )
}