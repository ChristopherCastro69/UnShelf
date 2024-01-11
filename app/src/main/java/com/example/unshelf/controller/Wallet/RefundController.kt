package com.example.unshelf.controller.Wallet

import com.example.unshelf.model.checkout.FullCheckoutModel
import com.example.unshelf.model.wallet.RefundRequest
import com.example.unshelf.model.wallet.RefundRequestAttributes
import com.example.unshelf.model.wallet.RefundRequestData
import com.example.unshelf.model.wallet.RefundResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class RefundController {
    val client = OkHttpClient()
    private val gson = Gson()

    suspend fun requestRefund(amount : Double, paymentID : String) : RefundResponse? {
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(
            mediaType,
            "{\"data\":{\"attributes\":{\"amount\":8000,\"payment_id\":\"pay_NChcBDpedkHd7E7BTy7x2hWe\",\"reason\":\"others\",\"notes\":\"Out of stock\"}}}"
        )
        val request = Request.Builder()
            .url("https://api.paymongo.com/refunds")
            .post(body)
            .addHeader("accept", "application/json")
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "Basic c2tfdGVzdF9VMkNSQ0o3UGlTOFpUSlN4VDluUGtLUzQ6")
            .build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            val retrievedRefund: RefundResponse = gson.fromJson(responseBody, RefundResponse::class.java)
            return retrievedRefund
        } else {
            println(response.code)
        }
        return null
    }
}