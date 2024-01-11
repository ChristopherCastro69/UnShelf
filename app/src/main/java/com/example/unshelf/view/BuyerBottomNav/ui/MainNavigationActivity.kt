package com.example.unshelf.view.BuyerBottomNav.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unshelf.controller.Checkout.CheckoutSessionController
import com.example.unshelf.controller.DataFetch.DataFetchController
import com.example.unshelf.controller.User.UserController
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.view.BuyerBottomNav.main.BuyerAppNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class MainNavigationActivityBuyer : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserController.getCustomerDetails()
        setContent{
            BuyerAppNavigation()
        }

//        lifecycleScope.launch {
//            val user = getUser()
//            // Now you can use the 'user' object as needed
//            Log.d("USER_IN_ACTIVITY", "$user")
//            // Example: Pass 'user' to a Composable function
//            setContent {
//                BuyerAppNavigation(user)
//            }
//        }
    }
}
