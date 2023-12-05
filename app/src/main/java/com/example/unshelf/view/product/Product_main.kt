package com.example.unshelf.view.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.unshelf.R

class product_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_main)

        val backBtn = findViewById<ImageView>(R.id.pm_icon_backBtn)
        val addCartBtn = findViewById<Button>(R.id.pm_addbasket_btn)

        addCartBtn.setOnClickListener{
            val intent = Intent(this, cart::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}