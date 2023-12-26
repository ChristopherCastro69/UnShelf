package com.example.unshelf.view.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.example.unshelf.controller.Checkout.CheckoutSessionController
import com.example.unshelf.model.checkout.CheckoutResponse
import com.example.unshelf.view.Wallet.Balance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CheckoutRedirect : ComponentActivity() {
    var checkoutID: String = ""
    var responseC: CheckoutResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val checkoutUrl = intent.getStringExtra("checkoutUrl")
        checkoutID = intent.getStringExtra("checkoutID") as String
        Log.d("CheckoutID", "$checkoutID")
        var clientKey = ""
        lifecycleScope.launch(Dispatchers.Default) {
            responseC = CheckoutSessionController().retrieveCheckout(checkoutID)
            if(responseC!=null)
                clientKey = responseC!!.data.attributes.clientKey
            Log.d("ClientKey", "$clientKey")
        }
        setContent {
            if (checkoutUrl != null && clientKey != null) {
                CheckoutRedirectContent(url = checkoutUrl, clientKey = clientKey, activity = this, cID = checkoutID)
            }
        }
    }
}

@Composable
fun CheckoutRedirectContent(url: String, clientKey: String, activity: ComponentActivity, cID: String) {
    val context = LocalContext.current
    var flag = false
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val curUrl = request?.url.toString()
                    if (curUrl!=url && curUrl.startsWith("https://checkout.paymongo.com/${clientKey}") && !flag) {
                        CoroutineScope(Dispatchers.Default).launch() {
                            flag = true
                            CheckoutSessionController().placeOrder(checkoutID = cID)
                        }
                        handleCheckoutRedirect(url, activity)
                        return true
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                }
            }
            loadUrl(url)
        }
    }, update = { view ->
        view.loadUrl(url)
    })
}

private fun handleCheckoutRedirect(url: String, activity: ComponentActivity) {
    val intent = Intent(activity, OrderPlaced::class.java)
    activity.startActivity(intent)

    activity.finish()
}