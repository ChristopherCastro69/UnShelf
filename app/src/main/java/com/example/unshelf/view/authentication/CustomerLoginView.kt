package com.example.unshelf.view.authentication
// CustomerLoginView.kt
// CustomerLoginView.kt
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.unshelf.view.MainActivity
import com.example.unshelf.databinding.ActivityCustomerLoginBinding

class CustomerLoginView(private val context: Context, private val binding: ActivityCustomerLoginBinding) {

    // Interface to communicate with the controller

    interface LoginListener {
        fun onLoginClicked(email: String, password: String)
        fun onGoogleLoginClicked() // Add this line
    }

    private var loginListener: LoginListener? = null

    fun setLoginListener(listener: LoginListener) {
        loginListener = listener
    }

    fun initViews() {
        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(context, CustomerRegister::class.java)
            context.startActivity(intent)
        }

        binding.buttonSeller.setOnClickListener{
            val intent = Intent(context, Seller_Login::class.java)
            context.startActivity(intent)
        }

        binding.tfForgotPassword.setOnClickListener {
            // Intent to launch ResetPasswordActivity
            val intent = Intent(context, ResetPassword::class.java)
            context.startActivity(intent)
        }

        binding.lblSeller.setOnClickListener{
            val intent = Intent(context, Seller_Login::class.java)
            context.startActivity(intent)
        }

        binding.backButton.setOnClickListener{
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

        binding.buttonLoginGoogle.setOnClickListener{
            loginListener?.onGoogleLoginClicked()
        }


    }




    private fun loginUser() {
        val email: String = binding.customerEmail.text.toString()
        val password: String = binding.customerPassword.text.toString()

        val isValidated: Boolean = validateData(email, password)
        if (!isValidated) {
            return
        }

        // Notify the listener (controller) to login
        loginListener?.onLoginClicked(email, password)
    }

    private fun validateData(email: String?, password: String): Boolean {
        // validate the data that are input by the user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.customerEmail.setError("Email is invalid")
            return false
        }
        if (password.length < 6) {
            binding.customerPassword.setError("Password length is invalid")
            return false
        }
        return true
    }

    fun changeInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.buttonLogin.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.buttonLogin.visibility = View.VISIBLE
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}
