package com.example.unshelf.controller.Checkout

import com.example.unshelf.model.checkout.*
import com.example.unshelf.model.entities.ProductDetailsModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
class CheckoutSessionController() {
    private val gson = Gson()

    suspend fun createCheckoutSession(): String {
        val salad = ProductDetailsModel(
            storeName = "Lucky's fruits",
            productName = "Fruit Salad",
            quantity = 2,
            price = 20.00,
            expirationDate = "12/8/2023",
            imageUrl = "https://i.pinimg.com/736x/3c/d5/dd/3cd5dd10fdaef74c958d22ef340b8bb0.jpg",
            category = null,
            description = null
        )
        val salad2 = ProductDetailsModel(
            storeName = "Top's fruits",
            productName = "Fruit Salad",
            quantity = 2,
            price = 20.00,
            expirationDate = "12/8/2023",
            imageUrl = "https://nationalvending.com/wp-content/uploads/2019/02/shutterstock_682616113-600x384.jpg",
            category = null,
            description = null
        )
        val liSalad = partLineItem(
            amount = (salad.price * 100).toInt(),
            name = salad.productName,
            description = salad.storeName,
            quantity = 2,
            images = listOf(salad.imageUrl),
        )
        val liSalad2 = partLineItem(
            amount = (salad2.price * 100).toInt(),
            name = salad2.productName,
            description = salad2.storeName,
            quantity = 2,
            images = listOf(salad2.imageUrl),
        )
        val basketList: List<partLineItem> = listOf(
            liSalad, liSalad, liSalad, liSalad2, liSalad2, liSalad2
        )
        val att = partAttributes(
            partBilling(email = "hgalosada@gmail.com", name = "Hernah Alosada", phone = "09551062604"),
            billing_information_fields_editable = "enabled",
            line_items = basketList,
            payment_method_types = listOf("gcash", "paymaya"),
            send_email_receipt = true,
            show_line_items = true
        )
        val testCheckout = partCheckout(partData(att))

        val checkoutJson: String = gson.toJson(testCheckout)

        try {
            val client = OkHttpClient()

            val mediaType = "application/json".toMediaTypeOrNull()
            val body = RequestBody.create(mediaType, checkoutJson)
            val request = Request.Builder()
                .url("https://api.paymongo.com/v1/checkout_sessions")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("authorization", "Basic c2tfdGVzdF9VMkNSQ0o3UGlTOFpUSlN4VDluUGtLUzQ6")
                .build()

            val response = withContext(Dispatchers.IO) {
                client.newCall(request).execute()
            }
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                return getCheckoutUrl(responseBody)
            } else {
                println(response.code)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }

    private fun getCheckoutUrl(resBody: String?): String {
        val retrievedCheckout: FullCheckoutModel = gson.fromJson(resBody, FullCheckoutModel::class.java)
        return retrievedCheckout.data.attributes.checkout_url
    }
}


