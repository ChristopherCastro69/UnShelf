package com.example.unshelf.model.wallet

import com.google.gson.annotations.SerializedName

data class RefundResponse(
    @SerializedName("data")
    val data: RefundDetails
)

data class RefundDetails(
    val id: String,
    val type: String,
    val attributes: RefundAttributes
)

data class RefundAttributes(
    val amount: Int,
    @SerializedName("balance_transaction_id")
    val balanceTransactionId: String?,
    val currency: String,
    val livemode: Boolean,
    val metadata: Any?,
    val notes: String?,
    @SerializedName("payment_id")
    val paymentId: String,
    @SerializedName("payout_id")
    val payoutId: String?,
    val reason: String,
    val status: String,
    @SerializedName("available_at")
    val availableAt: Long,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("refunded_at")
    val refundedAt: Long?, // You can replace 'Long?' with the actual type of refundedAt if known
    @SerializedName("updated_at")
    val updatedAt: Long
)