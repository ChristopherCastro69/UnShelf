package com.example.unshelf.view.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.view.MainActivity
import com.example.unshelf.controller.CustomerRegisterController
import com.example.unshelf.databinding.ActivityCustomerRegisterBinding
import com.example.unshelf.model.authentication.CustomerAuthModel

class CustomerRegister : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerRegisterBinding
    private val customerAuthModel = CustomerAuthModel()
    private val customerRegisterController = CustomerRegisterController(customerAuthModel)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener { createAccount() }

        binding.buttonLogin.setOnClickListener {
           val intent = Intent(this, Customer_Login::class.java)
           startActivity(intent)
       }

        binding.backArrowImage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.sellerButton.setOnClickListener {
            val intent = Intent(this, Seller_Register::class.java)
            startActivity(intent)
        }

        binding.tfSeller.setOnClickListener {
            val intent = Intent(this, Seller_Register::class.java)
            startActivity(intent)
        }
    }



    fun createAccount() {
        val email: String = binding.tfCustomerEmail.text.toString()
        val password: String = binding.tfCustomerPassword.text.toString()
        val confirmPassword: String = binding.tfConfirmPassword.text.toString()
        val fullName: String = binding.tfCustomerName.text.toString()
        val phoneNumberString: String = binding.tfCustomerPhone.text.toString()
        val address: String = "Cebu City"

        val isValidated: Boolean = CustomerAuthModel().validateData(email, password, confirmPassword)

        if (!isValidated) {
            return
        }

        changeInProgress(true) // Show ProgressBar

        try {
            val phoneNumber: Long = phoneNumberString.toLong()
            Log.d("CreateAccount", "Phone Number: $phoneNumber")

            customerRegisterController.createCustomerAccount(
                email,
                password,
                phoneNumber,
                fullName,
                address
            ) { success, message ->
                changeInProgress(false)
                // Handle the result of account creation
                if (success) {
                    showToast(message ?: "Account Successfully Created. Check email for verification")
                    // Perform other actions on success
                    navigateToLogin()
                } else {
                    showToast(message ?: "Failed to create account")
                    // Perform other actions on failure
                }
            }
        } catch (e: NumberFormatException) {
            Log.e("CreateAccount", "Error converting phone number to Long: $phoneNumberString")
            changeInProgress(false)
            showToast("Invalid phone number format")
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