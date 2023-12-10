package com.example.unshelf.view.BuyerBottomNav.screens

import android.view.LayoutInflater
import android.widget.Button
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.unshelf.R
import com.example.unshelf.helper.UI_Tester_Helper
import com.example.unshelf.view.AddressManager.UserAddress

@Composable
fun NearMe() {
    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_address_manager, null, false)
            val btnShareCurrAddress = view.findViewById<Button>(R.id.btnShareCurrAddress)
            val btnManualAddress = view.findViewById<Button>(R.id.btnManualAddress)

            UI_Tester_Helper.UI_Test(view.context, btnShareCurrAddress, UserAddress::class.java)
            UI_Tester_Helper.UI_Test(view.context, btnManualAddress, UserAddress::class.java)

            view
        },
        modifier = Modifier.fillMaxSize(), // Set the modifier to fill the maximum size
        update = { view ->
            // Update the view
        }
    )
}
