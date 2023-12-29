package com.example.unshelf.view.authentication

import CustomerLoginController
import android.content.Intent

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.example.unshelf.databinding.ActivityCustomerLoginBinding
import com.example.unshelf.model.authentication.LoginAuthenticationManager
import com.example.unshelf.view.authentication.CustomerLoginView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CustomerLoginController.GOOGLE_SIGN_IN_REQUEST) {
            controller.handleGoogleSignInResult(data)
        }
    }

    companion object {
        private const val GOOGLE_SIGN_IN_REQUEST = 1001
    }
}

