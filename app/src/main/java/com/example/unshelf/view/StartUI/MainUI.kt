package com.example.unshelf.view.StartUI

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.R
import com.example.unshelf.helper.UI_Tester_Helper
import com.example.unshelf.view.authentication.Customer_Login

class MainUI : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMain = findViewById<Button>(R.id.btn_get_started)

        UI_Tester_Helper.UI_Test(this, btnMain, Customer_Login::class.java);
    }
}
