package com.example.unshelf.model.checkout

import com.google.gson.annotations.SerializedName

//Contains required attributes for checkout session creation
data class partCheckout(
    val `data`: partData
)

data class partData(
    val attributes: partAttributes
)


data class partAttributes(
    val billing: partBilling,
    val line_items: List<partLineItem>,
    val payment_method_types: List<String>,
    val send_email_receipt: Boolean,
    val show_description: Boolean = false,
    val show_line_items: Boolean
)

data class partBilling(
    val email: String,
    val name: String,
    val phone: String
)

data class partLineItem(
    val amount: Int,
    val currency: String = "PHP",
    @SerializedName("description")
    val sellerID: String,
    val name: String,
    val quantity: Int,
    val images: List<String?>,
)

