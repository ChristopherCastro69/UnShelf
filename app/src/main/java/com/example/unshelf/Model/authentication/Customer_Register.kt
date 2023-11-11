package com.example.unshelf.Model.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.MainActivity
import com.example.unshelf.databinding.ActivityCustomerRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Customer_Register : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener(View.OnClickListener {
                v: View? -> createAccount()
        })

       binding.buttonLogin.setOnClickListener {
           val intent = Intent(this, Customer_Login::class.java)
           startActivity(intent)
       }

        binding.backArrowImage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.sellerButton.setOnClickListener {
            val intent = Intent(this, Seller_Login::class.java)
            startActivity(intent)
        }

        binding.tfSeller.setOnClickListener {
            val intent = Intent(this, Seller_Login::class.java)
            startActivity(intent)
        }
    }

//    ------------> FUNCTIONS <--------------

    fun createAccount() {
        val email: String = binding.tfCustomerEmail.text.toString()
        val password: String = binding.tfCustomerPassword.text.toString()
        val confirmPassword: String = binding.tfConfirmPassword.text.toString()
        val isValidated: Boolean = validateData(email, password, confirmPassword)
        if (!isValidated) {
            return
        }
        createAccountInFirebase(email, password)
    }
    fun createAccountInFirebase(email: String, password: String){
        changeInProgress(true);

        //Initialize Firebase
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@Customer_Register,
                OnCompleteListener<AuthResult?> { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        //creating acc is done
                        Utility.showToast(
                            this@Customer_Register,
                            "Account Successfully Created. Check email for verification"
                        )
                        firebaseAuth.currentUser!!.sendEmailVerification()
                        firebaseAuth.signOut()
                        finish()
                    } else {
                        Utility.showToast(
                            this@Customer_Register,
                            task.exception!!.localizedMessage
                        )
                        changeInProgress(false)
                    }
                }
            )
    }

    fun validateData(email: String?, password: String, confirmPassword: String?): Boolean {
        //validate the data that are input by the user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tfCustomerEmail.setError("Email is invalid")
            return false
        }
        if (password.length < 6) {
            binding.tfCustomerPassword.setError("Password length is invalid")
            return false
        }
        if (password != confirmPassword) {
            binding.tfConfirmPassword.setError("Password do not match")
            return false
        }
        return true
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
}