package com.example.unshelf.view.authentication

import CustomerLoginController

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.unshelf.databinding.ActivityCustomerLoginBinding
import com.example.unshelf.model.authentication.LoginAuthenticationManager
import com.example.unshelf.view.authentication.CustomerLoginView

class Customer_Login : AppCompatActivity() {
    private lateinit var binding: ActivityCustomerLoginBinding
    private lateinit var controller: CustomerLoginController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val view = CustomerLoginView(this, binding)
        val model = LoginAuthenticationManager()
        controller = CustomerLoginController(this, view, model)

        controller.init()
    }
}

