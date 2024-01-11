package com.example.unshelf.controller

import com.example.unshelf.model.entities.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderApprovalController {
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    fun acceptOrder(order: Order){
        CoroutineScope(Dispatchers.Default).launch{


        }
    }

    fun rejectOrder(orderId: String){

    }
}