package com.example.unshelf.controller

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.unshelf.controller.Checkout.CheckoutSessionController
import com.example.unshelf.controller.Wallet.RefundController
import com.example.unshelf.model.entities.Order
import com.example.unshelf.model.entities.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object OrderApprovalController {
    val db = FirebaseFirestore.getInstance()
    var quantityLow = mutableStateOf(false)
    fun acceptOrder(order: Order){
        CoroutineScope(Dispatchers.Default).launch{
            quantityLow.value = false
            val storeProducts = order.products
            val status = hashMapOf("orderStatus" to "accepted")
            for(product in storeProducts){
                val p_id = product.productID!!
                val data = db.collection("products").document(p_id).get().await()
                if(data.exists()){
                    var productFetch = data.toObject(Product::class.java)!!
                    if(productFetch.quantity < product.quantity){
                        quantityLow.value = true
                        return@launch
                    }
                    //println("P_ID" + p_id + " -> " + productFetch.quantity + " -> " + product.quantity)
                    productFetch.quantity -= product.quantity
                    db.collection("products").document(p_id).set(productFetch, SetOptions.merge()).await()
                }
            }
            db.collection("orders").document(order.orderID).set(status, SetOptions.merge()).await()
        }
    }

    fun rejectOrder(order: Order){
        CoroutineScope(Dispatchers.Default).launch{
            val status = hashMapOf("orderStatus" to "cancelled")
            db.collection("orders").document(order.orderID).set(status, SetOptions.merge()).await()
        }
    }

    fun completeOrder(order: Order, code: String){
        CoroutineScope(Dispatchers.Default).launch{
            if(order.refNo == code){
                val status = hashMapOf("orderStatus" to "completed")
                db.collection("orders").document(order.orderID).set(status, SetOptions.merge()).await()
            }
        }
    }

}