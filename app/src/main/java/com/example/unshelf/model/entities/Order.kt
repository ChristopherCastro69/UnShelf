package com.example.unshelf.model.entities

import com.example.unshelf.model.checkout.LineItem
import com.example.unshelf.model.checkout.partLineItem
import java.util.Date

data class Order(
    val checkoutID: String = "",
    val paymentID: String = "",
    val paymentTimestamp: Date? = null,
    val customerID: String = "",
    val products: List<LineItem> = listOf(),
    val totalAmount: Double = 0.0,
    val fee: Double = 0.0,
    val netAmount: Double = 0.0,
    val orderStatus: String = "",
    val paymentMethod: String = ""
)
