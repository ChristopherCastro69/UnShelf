package com.example.unshelf.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.unshelf.MainActivity
import com.example.unshelf.R

class Customer_Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_register)

        val backArrowButton = findViewById<ImageView>(R.id.backArrowImage)
        val sellerButton = findViewById<ImageView>(R.id.sellerButton)
        val sellerText = findViewById<TextView>(R.id.tf_seller)
        val signUpButton = findViewById<Button>(R.id.buttonSignUp)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        loginButton.setOnClickListener {
            val intent = Intent(this, Customer_Login::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, Customer_Register::class.java)
            startActivity(intent)
        }

        backArrowButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        sellerButton.setOnClickListener{
            val intent = Intent(this, Seller_Login::class.java)
            startActivity(intent)
        }

        sellerText.setOnClickListener {
            val intent = Intent(this, Seller_Login::class.java)
            startActivity(intent)
        }
    }
}