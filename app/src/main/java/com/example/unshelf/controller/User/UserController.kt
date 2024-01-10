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
    val userID = FirebaseAuth.getInstance().currentUser!!.uid
    var customer : Customer? = null
    fun getCustomerDetails() : Customer? {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                var user = Firebase.firestore.collection("customers").document(userID).get().await()
                if(user.exists())
                    customer = user!!.toObject(Customer::class.java)
                println("name: ${customer!!.fullName}")
            } catch (e : Exception) {
                Log.e("Profile", "Error retrieving user information", e)
            }
        }
        return customer
    }

    fun getSellerDetails() : Seller? {
        var seller : Seller? = null
        CoroutineScope(Dispatchers.Default).launch {
            try {
                var user = Firebase.firestore.collection("sellers").document(userID).get().await()
                seller = user.toObject(Seller::class.java)
            } catch(e : Exception) {

            }

        }
        return seller
    }
}