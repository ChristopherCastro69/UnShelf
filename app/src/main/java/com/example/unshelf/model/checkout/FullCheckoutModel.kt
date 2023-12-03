package com.example.unshelf.model.checkout

data class FullCheckoutModel(
    val `data`: FullData
)

data class FullData(
    val attributes: FullAttributes,
    val id: String,
    val type: String
)

data class FullAttributes(
    //required
    val billing: FullBilling,
    val billing_information_fields_editable: String,
    val cancel_url: Any,
    val checkout_url: String,
    val client_key: String,
    val created_at: Int,
    val customer_email: Any,
    val description: String,
    //required
    val line_items: List<FullLineItem>,
    val livemode: Boolean = false,
    val merchant: String = "UnShelf",
    val metadata: Any,
    val payment_intent: FullPaymentIntent,
    val payment_method_types: List<String>,
    val payments: List<Any>,
    //optional
    val reference_number: Any,
    val send_email_receipt: Boolean = true,
    val show_description: Boolean = false,
    val show_line_items: Boolean = true,
    val status: String,
    val success_url: Any,
    val updated_at: Int
)

data class FullBilling(
    val address: FullAddress,
    val email: String,
    val name: String,
    val phone: String
)

data class FullLineItem(
    val amount: Int,
    val currency: String = "PHP",
    val description: String,
    val images: List<String?>,
    val name: String,
    val quantity: Int
)

data class FullPaymentIntent(
    val attributes: FullAttributesX,
    val id: String,
    val type: String
)

data class FullAddress(
    val city: Any,
    val country: Any,
    val line1: Any,
    val line2: Any,
    val postal_code: Any,
    val state: Any
)

data class FullAttributesX(
    val amount: Int,
    val capture_type: String,
    val client_key: String,
    val created_at: Int,
    val currency: String,
    val description: String,
    val last_payment_error: Any,
    val livemode: Boolean,
    val metadata: Any,
    val next_action: Any,
    val payment_method_allowed: List<String> = listOf("gcash","paymaya"),
    val payment_method_options: FullPaymentMethodOptions,
    val payments: List<Any>,
    val setup_future_usage: Any,
    val statement_descriptor: String,
    val status: String,
    val updated_at: Int
)

data class FullPaymentMethodOptions(
    val card: FullCard
)

data class FullCard(
    val request_three_d_secure: String
)