package com.example.unshelf.Model.authentication

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.Model.entities.Products
import com.example.unshelf.Model.entities.Seller
import com.example.unshelf.Model.entities.Stores
import com.example.unshelf.databinding.ActivitySellerRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class Seller_Register : AppCompatActivity() {

    private lateinit var binding: ActivitySellerRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener(View.OnClickListener {
            v: View? -> createAccount()
        })

    }

    //    ------------> FUNCTIONS <--------------

    fun createAccount() {

        val email: String = binding.tfSellerEmail.text.toString()
        val password: String = binding.tfSellerPassword.text.toString()
        val confirmPassword: String = binding.tfConfirmPassword.text.toString()
        val fullName: String = binding.tfSellerName.text.toString()
        val phoneNumber:  Long = binding.tfSellerPhone.text.toString().toLong()
        val address : String = "Cebu City"
        val storeName : String = binding.tfSellerStoreName.text.toString()


        val productList: List<Products> = listOf(Products(1, 0), Products(2, 0))
        val rating : Long = 0;
        val status : Int = 0;
        val seller = Seller(email,password, phoneNumber, fullName, storeName, address)
        // Retrieve the sellerID using the getSellerID method
        val sellerID: Int = seller.getSellerID()

        val isValidated: Boolean = validateData(email, password, confirmPassword)
        if (!isValidated) {
            return
        }
        createAccountInFirebase(email, password, fullName,storeName, phoneNumber, address, sellerID, productList, rating, status )
    }

    fun createAccountInFirebase(
        email: String,
        password: String,
        fullName: String,
        storeName: String,
        phoneNumber: Long,
        address: String,
        sellerID: Int,
        productList: List<Products>,
        rating: Long,
        status: Int
    ){
        changeInProgress(true);

        //Initialize Firebase
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this@Seller_Register,
                OnCompleteListener<AuthResult?> { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        // Create a Customer object with the provided details
                        val seller = Seller(email,password, phoneNumber, fullName, storeName, address)
                        val store = Stores(sellerID, storeName, rating, productList, status)

                        // Get the current user's UID
                        val uid = firebaseAuth.currentUser!!.uid

                        // Store the Seller details in Firestore under the "Customers" collection
                        firestore.collection("sellers").document(uid)
                            .set(seller)
                            .addOnSuccessListener {
                                Utility.showToast(this@Seller_Register, "Account Successfully Created. Check email for verification")
                                firebaseAuth.currentUser!!.sendEmailVerification()
                                firebaseAuth.signOut()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Utility.showToast(this@Seller_Register, "Failed to store Seller details: ${e.localizedMessage}")
                                changeInProgress(false)
                            }
                        firestore.collection("stores").document(uid)
                            .set(store)
                            .addOnSuccessListener {
                                Utility.showToast(this@Seller_Register, "Store Successfully Created. Check admin for verification")
                                firebaseAuth.signOut()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Utility.showToast(this@Seller_Register, "Failed to store Stores details: ${e.localizedMessage}")
                                changeInProgress(false)
                            }

                    } else {
                        Utility.showToast(this@Seller_Register, task.exception!!.localizedMessage)
                        changeInProgress(false)
                    }
                }
            )
    }

    fun validateData(email: String?, password: String, confirmPassword: String?): Boolean {
        //validate the data that are input by the user
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tfSellerEmail.setError("Email is invalid")
            return false
        }
        if (password.length < 6) {
            binding.tfSellerPassword.setError("Password length is invalid")
            return false
        }
        if (password != confirmPassword) {
            binding.tfConfirmPassword.setError("Passwords do not match")
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