package com.example.unshelf.model

import com.example.unshelf.model.checkout.LineItem
import java.util.Date

class CartModel (
    val customerID: String = "",
    var products: List<LineItem> = listOf(),
    val totalAmount: Double = 0.0,
)