package com.example.unshelf.model.entities

import java.util.Date

class Product(
    val productName: String,
    val categories: List<String>,  // Changed from String to List<String>
    val thumbnail: String,
    val gallery: String,
    val description: String,
    val marketPrice: Long,
    val hashtags: List<String>,   // Changed from String to List<String>
    val expirationDate: String,      // Changed from String to Date
    val discount: Long,
    val quantity: Int // Added quantity field
    )


