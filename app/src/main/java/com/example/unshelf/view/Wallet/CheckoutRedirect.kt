package com.example.unshelf.view.Wallet

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView


class CheckoutRedirect() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val checkoutUrl = intent.getStringExtra("checkoutUrl")
        println("checkoutUrl: $checkoutUrl")
        setContent {
            if (checkoutUrl != null) {
                CheckoutRedirectContent(url = checkoutUrl)
            }
        }
    }
}

@Composable
fun CheckoutRedirectContent(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = { view ->
        view.loadUrl(url)
    })
}

