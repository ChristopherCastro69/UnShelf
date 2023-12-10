package com.example.unshelf.model.entities

import android.os.Parcelable
import androidx.compose.runtime.MutableState
import java.util.Date
import kotlinx.parcelize.Parcelize



@Parcelize
class Product(
    var productID : String ="",
    var sellerID: String ="",
    var storeID : String ="",
    val productName: String = "",
    val quantity: Int = 0, // Added quantity field
    val price: Double = 0.0,
    val sellingPrice : Double = 0.0,
    val discount : Double = 0.0,
    val voucherCode : String = "",
    val categories: List<String> = listOf(""),  // Changed from String to List<String>
    val thumbnail: String = "",
    val description: String = "",
    val expirationDate: String ="",      // Changed from String to Date
    val isActive : Boolean = true
) : Parcelable
/*
 docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    productName.value = document.getString("productName") ?: ""
                    selectedCategory.value = document.getString("category") ?: "Grocery"
                    val thumbnailString = document.getString("thumbnail")
                    if (!thumbnailString.isNullOrEmpty()) {
                        imageUri.value = Uri.parse(thumbnailString)
                    } else {
                        // Handle the case where thumbnail string is null or empty
                        // Maybe set a default imageUri or leave it null based on your app's logic
                    }

                    productDescription.value = document.getString("description") ?: ""

                    // Convert String to Long for marketPrice
                    marketPrice.value = document.getDouble("price")?.toString() ?: ""

                    // Convert String to List<String> for galleryImageUris
//                    val galleryImages = document.getString("gallery")?.split(",") ?: listOf()
//                    galleryImageUris.clear()
//                    galleryImages.forEach { uriString ->
//                        galleryImageUris.add(Uri.parse(uriString))
//                    }

//                    // Handle hashtags as List<String>
//                    val hashtags = document.get("hashtags") as? List<String> ?: listOf()
//                    productHashtags.clear()
//                    productHashtags.addAll(hashtags)

                    // Handle expirationDate
                    val expirationDateString = document.getString("expirationDate") ?: ""
                    if (expirationDateString.isNotEmpty()) {
                        try {
                            pickedDate.value = LocalDate.parse(expirationDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                        } catch (e: DateTimeParseException) {
                            Log.e("AddProduct", "Error parsing date: $expirationDateString", e)
                            // Set to default date or handle the error as needed
                            pickedDate.value = LocalDate.now()
                        }
                    } else {
                        pickedDate.value = LocalDate.now() // Or your default date
                    }

                    // Handle discount
                    discountPercent.value = document.getLong("discount")?.toString() ?: ""

                    // Handle quantity
                    productQuantity.value = document.getLong("quantity")?.toString() ?: ""
                } else {
                    Log.d("Firestore", "No such document")
                }
            }.addOnFailureListener { exception ->
                Log.d("Firestore", "get failed with ", exception)
            }
 */
