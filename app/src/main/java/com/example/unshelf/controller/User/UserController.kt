package com.example.unshelf.controller.User

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.unshelf.model.entities.Customer
import com.example.unshelf.model.entities.Seller
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object UserController {
    var customer : Customer? = null
    var seller : Seller? = null
    fun getCustomerDetails() : Customer? {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        CoroutineScope(Dispatchers.Default).launch {
            try {
                var user = Firebase.firestore.collection("customers").document(userID).get().await()
                if(user.exists())
                    customer = user!!.toObject(Customer::class.java)
            } catch (e : Exception) {
                Log.e("Profile", "Error retrieving user information", e)
            }
        }
        return customer
    }

    fun getSellerDetails() : Seller? {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        CoroutineScope(Dispatchers.Main).launch {
            try {
                var user = Firebase.firestore.collection("sellers").document(userID).get().await()
                if(user.exists())
                    seller = user!!.toObject(Seller::class.java)
            } catch(e : Exception) {
                Log.e("Profile", "Error retrieving user information", e)
            }

        }
        return seller
    }
}