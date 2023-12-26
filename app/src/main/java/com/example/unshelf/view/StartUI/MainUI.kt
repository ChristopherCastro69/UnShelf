package com.example.unshelf.view.StartUI

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.R
import com.example.unshelf.helper.UI_Tester_Helper
import com.example.unshelf.view.authentication.Customer_Login

class MainUI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMain = findViewById<Button>(R.id.btn_get_started)
        val spinner: Spinner = findViewById(R.id.spinnerAddress)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.city_options,
            R.layout.custom_spinner_item  // Use your custom layout here
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_item)  // Use your custom layout for the dropdown view as well
        spinner.adapter = adapter




        UI_Tester_Helper.UI_Test(this, btnMain, MainMarketplace::class.java);
    }
}
