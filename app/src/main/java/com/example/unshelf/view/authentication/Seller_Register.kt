package com.example.unshelf.view.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.controller.SellerAuthController
import com.example.unshelf.databinding.ActivitySellerRegisterBinding
import com.example.unshelf.model.authentication.SellerAuthModel
import com.example.unshelf.model.entities.Product
import com.example.unshelf.model.entities.Seller


class Seller_Register : AppCompatActivity() {

    private lateinit var binding: ActivitySellerRegisterBinding
    private val sellerAuthModel = SellerAuthModel()
    private val sellerAuthController = SellerAuthController(sellerAuthModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener{
            val intent = Intent(this, Seller_Login::class.java)
            startActivity(intent)
        }
        binding.buttonSignUp.setOnClickListener { createAccount() }

    }

    //    ------------> FUNCTIONS <--------------
    // The functions involve logic, please transfer these to the controller
    fun createAccount() {

        val email: String = binding.tfSellerEmail.text.toString()
        val password: String = binding.tfSellerPassword.text.toString()
        val confirmPassword: String = binding.tfConfirmPassword.text.toString()
        val fullName: String = binding.tfSellerName.text.toString()
        val phoneNumber:  Long = binding.tfSellerPhone.text.toString().toLong()
        val address : String = "Cebu City"
        val storeName : String = binding.tfSellerStoreName.text.toString()
        val adminVerified : String = "Requires Action"

        val rating : Double = 0.0;
        val followers : Int = 0;


        val isValidated: Boolean = SellerAuthModel().validateData(email, password, confirmPassword)
        if (!isValidated) {
            return
        }
        changeInProgress(true) // Show ProgressBar
        sellerAuthController.createSellerAccount(
            email,
            password,
            phoneNumber,
            fullName,
            address,
            storeName,
            rating,
            followers,
            adminVerified){ success, message ->
            changeInProgress(false)
            // Handle the result of account creation
            if (success) {
                showToast(message ?: "Account Successfully Created. Check email for verification")
                // Perform other actions on success
                navigateToLogin();
            } else {
                showToast(message ?: "Failed to create account")
                // Perform other actions on failure
            }
        }
    }



    fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.setVisibility(View.VISIBLE)
            binding.buttonSignUp.setVisibility(View.GONE)
        } else {
            binding.progressBar.setVisibility(View.GONE)
            binding.buttonSignUp.setVisibility(View.VISIBLE)
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, Customer_Login::class.java)
        startActivity(intent)
    }
    private fun showToast(message: String) {
        // Implement showToast method (e.g., using Toast.makeText)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}