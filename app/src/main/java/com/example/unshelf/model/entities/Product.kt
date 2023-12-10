package com.example.unshelf.model.entities

import androidx.compose.runtime.MutableState
import java.util.Date



class Product(
    var productID : String,
    var sellerID: String,
    var storeID : String,
    val productName: String,
    val quantity: Int, // Added quantity field
    val price: Long,
    val sellingPrice : Long,
    val discount : Long,
    val categories: List<String>,  // Changed from String to List<String>
    val thumbnail: String,
    val description: String,
    val expirationDate: String,      // Changed from String to Date
    val isActive : Boolean
)

//class sampleProduct(
//    val productName: String,
//    val categories: List<String>,  // Changed from String to List<String>
//    val thumbnail: String,
//    val gallery: String,
//    val description: String,
//    val marketPrice: Long,
//    val hashtags: List<String>,   // Changed from String to List<String>
//    val expirationDate: String,      // Changed from String to Date
//    val discount: Long,
//    val quantity: Int // Added quantity field
//)

//class ProductWithID(
//    val id : String,
//    val productName: String,
//    val categories: List<String>,  // Changed from String to List<String>
//    val thumbnail: String,
//    val gallery: String,
//    val description: String,
//    val marketPrice: Long,
//    val hashtags: List<String>,   // Changed from String to List<String>
//    val expirationDate: String,      // Changed from String to Date
//    val discount: Long,
//    val quantity: Int // Added quantity field
//)


