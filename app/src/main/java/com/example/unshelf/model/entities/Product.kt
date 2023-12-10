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
    val storeName : String = "",
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
    val active : Boolean = true

)
