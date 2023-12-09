package com.example.unshelf.view.BuyerBottomNav.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unshelf.controller.Checkout.CheckoutSessionController
import com.example.unshelf.controller.DataFetch.DataFetchController
import com.example.unshelf.view.BuyerBottomNav.main.BuyerAppNavigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainNavigationActivityBuyer : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BuyerAppNavigation()
        }
    }
}
