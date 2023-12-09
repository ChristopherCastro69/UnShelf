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
import com.example.unshelf.view.SellerBottomNav.screens.listings.productAdditionSuccess
import com.example.unshelf.view.SellerBottomNav.screens.listings.voucherCode
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

fun fetchUserDetails(onComplete: (String, String) -> Unit) {
    val userId = Firebase.auth.currentUser?.uid ?: return
    val sellerId = userId

    // Query Firestore 'stores' collection to find the store corresponding to the sellerId
    Firebase.firestore.collection("stores")
        .whereEqualTo("sellerID", sellerId) // Assuming the store documents have a 'sellerID' field
        .get()
        .addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                val storeDocument = querySnapshot.documents.firstOrNull()

                // Fetch the storeID from the store document
                val storeId = storeDocument?.getString("storeID") ?: ""
                Log.d("UserDetails", "Seller ID: $sellerId, Store ID: $storeId")

                // Return the seller ID and store ID
                onComplete(sellerId, storeId)
            } else {
                Log.d("UserDetails", "No store found for Seller ID: $sellerId")
            }
        }
        .addOnFailureListener {
            Log.e("Firestore", "Error fetching store details", it)
        }
}




fun saveProductToFirestore(sellerId: String, storeId: String, product: Product) {
    Log.d("Firestore", "Adding new product")
    val db = Firebase.firestore

    // Create a new document reference without specifying the ID for a new product
    val newProductRef = db.collection("products").document() // Firestore generates a new ID

    // Set the productID with the auto-generated ID
    product.productID = newProductRef.id
    // Set the sellerId and storeId
    product.sellerID = sellerId
    product.storeID = storeId

    newProductRef.set(product)
        .addOnSuccessListener {
            Log.d("Firestore", "New product added successfully, Product ID: ${product.productID}")
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

    val productRef = db.collection("products").document(productId)

    productRef.update(
        mapOf(

            "productName" to product.productName,
            "categories" to product.categories,
            "thumbnail" to product.thumbnail,
            "description" to product.description,
            "expirationDate" to product.expirationDate,
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


fun uploadImage(uri: Uri, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
    val storageRef = Firebase.storage.reference
    val fileName = "images/${UUID.randomUUID()}.jpg" // Unique file name
    val imageRef = storageRef.child(fileName)

    Log.d("ImageUpload", "Starting upload for: $uri")
    imageRef.putFile(uri)
        .addOnSuccessListener { taskSnapshot ->
            Log.d("ImageUpload", "Upload succeeded. Bytes Transferred: ${taskSnapshot.bytesTransferred}")
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                Log.d("ImageUpload", "Image URL: $downloadUri")
                onSuccess(downloadUri.toString())
            }.addOnFailureListener { exception ->
                Log.e("ImageUpload", "Failed to get download URL", exception)
                onFailure(exception)
            }
        }
        .addOnFailureListener { exception ->
            Log.e("ImageUpload", "Upload failed", exception)
            onFailure(exception)
        }
        .addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
            Log.d("ImageUpload", "Upload is $progress% done")
        }
}

fun fetchProductDetails(productId: String, onSuccess: (Product) -> Unit, onFailure: (Exception) -> Unit) {
    Firebase.firestore.collection("products").document(productId)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                try {
                    // Assuming 'Product' has a constructor that matches the document fields
                    val product = Product(
                        productID = document.id,
                        sellerID = document.getString("sellerId") ?: "",
                        storeID = document.getString("storeId") ?: "",
                        productName = document.getString("productName") ?: "",
                        quantity = document.getLong("quantity")?.toInt() ?: 0,
                        price = document.getDouble("price") ?: 0.0,
                        sellingPrice = document.getDouble("sellingPrice") ?: 0.00,
                        discount = document.getDouble("discount") ?: 0.00,
                        voucherCode = document.getString("voucherCode") ?: "",
                        categories = document.get("categories") as? List<String> ?: emptyList(),
                        thumbnail = document.getString("thumbnail") ?: "",
                        description = document.getString("description") ?: "",
                        expirationDate = document.getString("expirationDate") ?: "",
                        isActive = document.getBoolean("isActive") ?: false
                    )
                    onSuccess(product)
                } catch (e: Exception) {
                    onFailure(e)
                }
            }
        }
        .addOnFailureListener(onFailure)
}
