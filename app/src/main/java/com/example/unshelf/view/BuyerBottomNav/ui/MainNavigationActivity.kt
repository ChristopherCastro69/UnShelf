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

        lifecycleScope.launch {
            val user = getUser()
            // Now you can use the 'user' object as needed
            Log.d("USER_IN_ACTIVITY", "$user")
            // Example: Pass 'user' to a Composable function
            setContent {
                BuyerAppNavigation(user)
            }
        }
    }
}

suspend fun getUser(): Customer? {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val currentUser = auth.currentUser

    return if (currentUser != null) {
        val uid = currentUser.uid

        try {
            val customerQuerySnapshot = db.collection("customers")
                .document(uid)
                .get()
                .await()

            Log.d("CUSTOMERFETCH", "${customerQuerySnapshot}")
            val customerData = customerQuerySnapshot?.toObject(Customer::class.java)

            Log.d("CUSTOMERDATA", "aaaaaa${customerData}")

            customerData
        } catch (e: Exception) {
            Log.d("CUSTOMERDATA", "${e}")
            null
        }
    } else {
        null // No user is signed in
    }
}