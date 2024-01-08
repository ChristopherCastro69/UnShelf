package com.example.unshelf.controller

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object OrderController : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    var orderList = mutableStateOf(listOf<Order>())
    var storeOrders = listOf<Order>()
    var isLoading = mutableStateOf(true)

    fun fetchOrder(){
        CoroutineScope(Dispatchers.Default).launch {
            val orderFetch = db.collection("orders").get().await()
            orderList.value = orderFetch.toObjects(Order::class.java)
            var storeOrder: Order? = null
            for (order in orderList.value) {
                val items = order.products
                for (item in items) {
                    if (item.sellerID.equals(currentUser)) {
                        if (storeOrder == null) {
                            storeOrder = Order(
                                order.checkoutID,
                                order.paymentID,
                                order.paymentTimestamp,
                                order.customerID,
                                listOf(),
                                0.0,
                                0.0,
                                0.0,
                                0.0,
                                order.orderStatus,
                                order.paymentMethod
                            )
                        }
                        storeOrder.products = storeOrder.products.plus(item)
                    }
                }
                if (storeOrder != null) {
                    storeOrders = storeOrders.plus(storeOrder)
                }
                storeOrder = null
            }
            orderList.value = storeOrders
            isLoading.value = false
            println("isLoading: " + isLoading)
            for(order in orderList.value) {
                println("OrderList " + order.checkoutID + " amount: " + order.netAmount)
            }
        }

    }

}