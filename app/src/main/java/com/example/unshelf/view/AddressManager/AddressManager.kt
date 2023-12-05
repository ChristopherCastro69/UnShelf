package com.example.unshelf.view.AddressManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.unshelf.R
import com.example.unshelf.helper.UI_Tester_Helper

class AddressManager : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_manager)

        val btnShareCurrAddress = findViewById<Button>(R.id.btnShareCurrAddress);
        val btnManualAddress = findViewById<Button>(R.id.btnManualAddress);

        UI_Tester_Helper.UI_Test(this, btnShareCurrAddress, UserAddress::class.java);
        UI_Tester_Helper.UI_Test(this, btnManualAddress, UserAddress::class.java);
    }


}