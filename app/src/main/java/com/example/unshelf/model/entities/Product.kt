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
    var storeName : String = "",
    var productName: String = "",
    var quantity: Int = 0, // Added quantity field
    var price: Double = 0.0,
    var sellingPrice : Double = 0.0,
    var discount : Double = 0.0,
    var voucherCode : String = "",
    var categories: List<String> = listOf(""),  // Changed from String to List<String>
    var thumbnail: String = "",
    var description: String = "",
    var expirationDate: String ="",      // Changed from String to Date
    var active : Boolean = true

) : Parcelable
