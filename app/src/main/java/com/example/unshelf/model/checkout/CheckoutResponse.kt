package com.example.unshelf.model.checkout
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CheckoutResponse(
    val data: SessionData
)

data class SessionData(
    val id: String,
    val type: String,
    val attributes: SessionAttributes
)

data class SessionAttributes(
    val billing: Billing,
    @SerializedName("billing_information_fields_editable")
    val billingInformationFieldsEditable: String,
    @SerializedName("cancel_url")
    val cancelUrl: String?,
    @SerializedName("checkout_url")
    val checkoutUrl: String,
    @SerializedName("client_key")
    val clientKey: String,
    @SerializedName("customer_email")
    val customerEmail: String?,
    val description: String?,
    @SerializedName("line_items")
    val lineItems: List<LineItem>,
    val livemode: Boolean,
    val merchant: String,
    val origin: String?,
    @SerializedName("paid_at")
    val paidAt: Long,
    val payments: List<Payment>,
    @SerializedName("payment_intent")
    val paymentIntent: PaymentIntent,
    @SerializedName("payment_method_types")
    val paymentMethodTypes: List<String>,
    @SerializedName("payment_method_used")
    val paymentMethodUsed: String,
    @SerializedName("reference_number")
    val referenceNumber: String?,
    @SerializedName("send_email_receipt")
    val sendEmailReceipt: Boolean,
    @SerializedName("show_description")
    val showDescription: Boolean,
    @SerializedName("show_line_items")
    val showLineItems: Boolean,
    val status: String,
    @SerializedName("success_url")
    val successUrl: String?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("updated_at")
    val updatedAt: Long,
    val metadata: Any?
)

data class Billing(
    val address: Address,
    val email: String?,
    val name: String?,
    val phone: String?
)

data class Address(
    val city: String?,
    val country: String?,
    val line1: String?,
    val line2: String?,
    @SerializedName("postal_code")
    val postalCode: String?,
    val state: String?
)

data class LineItem(
    var amount: Double,
    val currency: String,
    @SerializedName("description")
    val sellerID: String?,
    val images: List<String>,
    val name: String,
    val quantity: Int
){
    constructor() : this(0.0, "", "", listOf(), "", 0)
}

@Parcelize
data class OrderLineItem(
    var amount: Double,
    val currency: String,
    val images: List<String>,
    val name: String,
    val quantity: Int
) : Parcelable {
    constructor() : this(0.0, "", listOf(),"",  0)
}




data class Payment(
    val id: String,
    val type: String,
    val attributes: PaymentAttributes
)

data class PaymentAttributes(
    @SerializedName("access_url")
    val accessUrl: String?,
    val amount: Int,
    @SerializedName("balance_transaction_id")
    val balanceTransactionId: String,
    val billing: Billing,
    val currency: String,
    val description: String?,
    val disputed: Boolean,
    @SerializedName("external_reference_number")
    val externalReferenceNumber: String?,
    val fee: Int,
    val livemode: Boolean,
    val netAmount: Int,
    val origin: String,
    @SerializedName("payment_intent_id")
    val paymentIntentId: String,
    val payout: Any?,
    val source: Source,
    @SerializedName("statement_descriptor")
    val statementDescriptor: String,
    val status: String,
    @SerializedName("tax_amount")
    val taxAmount: Any?,
    val metadata: Any?,
    val refunds: List<Any>,
    val taxes: List<Any>,
    @SerializedName("available_at")
    val availableAt: Long,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("credited_at")
    val creditedAt: Long,
    @SerializedName("paid_at")
    val paidAt: Long,
    @SerializedName("updated_at")
    val updatedAt: Long
)

data class Source(
    val id: String,
    val type: String
)

data class PaymentIntent(
    val id: String,
    val type: String,
    val attributes: PaymentIntentAttributes
)

data class PaymentIntentAttributes(
    val amount: Int,
    @SerializedName("capture_type")
    val captureType: String,
    @SerializedName("client_key")
    val clientKey: String,
    val currency: String,
    val description: String?,
    val livemode: Boolean,
    @SerializedName("statement_descriptor")
    val statementDescriptor: String,
    val status: String,
    @SerializedName("last_payment_error")
    val lastPaymentError: Any?,
    @SerializedName("payment_method_allowed")
    val paymentMethodAllowed: List<String>,
    val payments: List<Payment>,
    @SerializedName("next_action")
    val nextAction: Any?,
    @SerializedName("payment_method_options")
    val paymentMethodOptions: Any?,
    val metadata: Any?,
    @SerializedName("setup_future_usage")
    val setupFutureUsage: Any?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("updated_at")
    val updatedAt: Long
)
