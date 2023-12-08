package com.example.unshelf.model.firestore.seller.dashboardmodel

import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.unshelf.model.entities.Product
import com.example.unshelf.ui.theme.PalmLeaf
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.sellerId
import com.example.unshelf.view.SellerBottomNav.screens.dashboard.storeId
import com.example.unshelf.view.SellerBottomNav.screens.listings.AddButton
import com.example.unshelf.view.SellerBottomNav.screens.listings.Category
import com.example.unshelf.view.SellerBottomNav.screens.listings.ExpirationDate
import com.example.unshelf.view.SellerBottomNav.screens.listings.Marketprice
import com.example.unshelf.view.SellerBottomNav.screens.listings.ProdName
import com.example.unshelf.view.SellerBottomNav.screens.listings.ProductDescription
import com.example.unshelf.view.SellerBottomNav.screens.listings.QuantityInput
import com.example.unshelf.view.SellerBottomNav.screens.listings.Thumbnail
import com.example.unshelf.view.SellerBottomNav.screens.listings.Voucher
import com.example.unshelf.view.SellerBottomNav.screens.listings.discountPercent
import com.example.unshelf.view.SellerBottomNav.screens.listings.galleryImageUris
import com.example.unshelf.view.SellerBottomNav.screens.listings.imageUri
import com.example.unshelf.view.SellerBottomNav.screens.listings.marketPrice
import com.example.unshelf.view.SellerBottomNav.screens.listings.pickedDate
import com.example.unshelf.view.SellerBottomNav.screens.listings.productAdditionSuccess
import com.example.unshelf.view.SellerBottomNav.screens.listings.productDescription
import com.example.unshelf.view.SellerBottomNav.screens.listings.productHashtags
import com.example.unshelf.view.SellerBottomNav.screens.listings.productName
import com.example.unshelf.view.SellerBottomNav.screens.listings.productQuantity
import com.example.unshelf.view.SellerBottomNav.screens.listings.selectedCategory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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

