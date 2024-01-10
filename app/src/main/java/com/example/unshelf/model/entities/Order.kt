package com.example.unshelf.model.entities

import android.os.Parcelable
import com.example.unshelf.model.checkout.LineItem
import com.example.unshelf.model.checkout.OrderLineItem
import com.example.unshelf.model.checkout.partLineItem
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
class Order(
    val refNo: String = "",
    val checkoutID: String = "",
    val paymentID: String = "",
    val paymentTimestamp: Date? = null,
    val customerID: String = "",
    val sellerID: String = "",
    var products: List<OrderLineItem> = emptyList(),
    val totalAmount: Double = 0.0,
    val paymongoFee: Double = 0.0,
    val unshelfFee: Double = 0.0,
    val netAmount: Double = 0.0,
    val orderStatus: String = "",
    val paymentMethod: String = ""
) : Parcelable
