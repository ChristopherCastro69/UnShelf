package com.example.unshelf.view.authentication

// Seller_Login.kt
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.controller.SellerLoginController
import com.example.unshelf.databinding.ActivitySellerLoginBinding
import com.example.unshelf.model.authentication.LoginAuthenticationManager

class Seller_Login : AppCompatActivity() {
    private lateinit var binding: ActivitySellerLoginBinding
    private lateinit var controller: SellerLoginController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val view = SellerLoginView(this, binding)
        val model = LoginAuthenticationManager()
        controller = SellerLoginController(this, view, model)

        controller.init()
    }
}
