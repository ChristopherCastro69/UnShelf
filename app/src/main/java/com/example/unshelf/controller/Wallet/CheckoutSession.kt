package com.example.unshelf.controller.Wallet

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.create
import okhttp3.Request


class CheckoutSession {
    val client = OkHttpClient()

    val mediaType = "application/json".toMediaTypeOrNull()
    val body = RequestBody.create(mediaType, "{\"data\":{\"attributes\":{\"send_email_receipt\":false,\"show_description\":false,\"show_line_items\":true,\"line_items\":[{\"currency\":\"PHP\",\"amount\":200000,\"name\":\"bread\",\"quantity\":3}],\"payment_method_types\":[\"gcash\"]}}}")
    val request = Request.Builder()
        .url("https://api.paymongo.com/v1/checkout_sessions")
        .post(body)
        .addHeader("accept", "application/json")
        .addHeader("Content-Type", "application/json")
        .addHeader("authorization", "Basic c2tfdGVzdF9VMkNSQ0o3UGlTOFpUSlN4VDluUGtLUzQ6")
        .build()

    val response = client.newCall(request).execute()
}