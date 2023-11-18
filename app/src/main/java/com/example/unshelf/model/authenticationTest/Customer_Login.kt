package com.example.unshelf.model.authenticationTest

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.MainActivity
import com.example.unshelf.databinding.ActivityCustomerLoginBinding
import com.example.unshelf.view.authentication.CustomerRegister
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Customer_Login : AppCompatActivity() {
    private lateinit var binding:ActivityCustomerLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initialize the Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this, CustomerRegister::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener(View.OnClickListener {
                v: View? -> loginUser()
        })
    }

//    ----------> FUNCTIONS <---------
    fun loginUser() {
        val email: String = binding.customerEmail.text.toString()
        val password: String = binding.customerPassword.text.toString()
        val isValidated: Boolean = validateData(email, password)
        if (!isValidated) {
            return
        }
        loginAccountInFirebase(email, password)
    }

    private fun loginAccountInFirebase(email: String, password: String) {
        changeInProgress(true)
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this@Customer_Login,
            OnCompleteListener<AuthResult?> { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    //Login is successful
                    if (firebaseAuth.currentUser!!.isEmailVerified) {
                        //go to main activity
                        startActivity(Intent(this@Customer_Login, MainActivity::class.java))
                    } else {
                        Utility.showToast(
                            this@Customer_Login,
                            "Email not verified, Please verify your email."
                        )
                        changeInProgress(false)
                    }
                } else {
                    Utility.showToast(this@Customer_Login, task.exception!!.localizedMessage)
                    changeInProgress(false)
                }
            }
        )
    }

    fun validateData(email: String?, password: String): Boolean {
        //validate the data that aere input by the user
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
            binding.progressBar.setVisibility(View.VISIBLE)
            binding.buttonLogin.setVisibility(View.GONE)
        } else {
            binding.progressBar.setVisibility(View.GONE)
            binding.buttonLogin.setVisibility(View.VISIBLE)
        }
    }
}
