package com.example.unshelf.model.wallet

import com.google.gson.annotations.SerializedName

data class RefundRequest (
    val data: RefundRequestData
)

data class RefundRequestData(
    val attributes: RefundRequestAttributes
)

data class RefundRequestAttributes(
    val amount: Int,
    @SerializedName("payment_id")
    val paymentId: String,
    val reason: String,
    val notes: String
)