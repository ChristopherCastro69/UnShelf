package com.example.unshelf.model.checkout
//Contains required attributes for checkout session creation
data class partCheckout(
    val `data`: partData
)

data class partData(
    val attributes: partAttributes
)


data class partAttributes(
    val billing: partBilling,
    val billing_information_fields_editable: String,
    val line_items: List<partLineItem>,
    val payment_method_types: List<String>,
    val send_email_receipt: Boolean,
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
    val description: String,
    val name: String,
    val quantity: Int,
    val images: List<String?>,
)

