package com.example.unshelf.view.authentication
// SellerLoginView.kt
// SellerLoginView.kt
import android.content.Context
import android.content.Intent
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.unshelf.databinding.ActivitySellerLoginBinding

class SellerLoginView(private val context: Context, private val binding: ActivitySellerLoginBinding) {

    interface LoginListener {
        fun onLoginClicked(email: String, password: String)
    }

    private var loginListener: LoginListener? = null

    fun setLoginListener(listener: LoginListener) {
        loginListener = listener
    }

    fun initViews() {

        binding.buttonSignUp.setOnClickListener{
            val intent = Intent(context, Seller_Register::class.java)
            context.startActivity(intent)
        }

        binding.buttonBack.setOnClickListener{
            val intent = Intent(context, Seller_Login::class.java)
            context.startActivity(intent)
        }
        binding.buttonLogin.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email: String = binding.SellerEmail.text.toString()
        val password: String = binding.tfSellerPassword.text.toString()

        val isValidated: Boolean = validateData(email, password)
        if (!isValidated) {
            return
        }

        loginListener?.onLoginClicked(email, password)
    }

    private fun validateData(email: String?, password: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.SellerEmail.setError("Email is invalid")
            return false
        }
        if (password.length < 6) {
            binding.tfSellerPassword.setError("Password length is invalid")
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
