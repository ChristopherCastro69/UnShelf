package com.example.unshelf.controller

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Order
import com.example.unshelf.model.entities.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

class OrderController : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    var orderList = listOf<Order>()
    var storeOrders = listOf<Order>()
    val _orders = MutableStateFlow<List<Order>>(listOf())
    val orders = _orders.asStateFlow()
    fun fetchOrder(){
        db.collection("orders").get()
            .addOnSuccessListener { documents ->
                orderList = documents.toObjects(Order::class.java)
                Log.d("OrderList", "${orderList}")
                var storeOrder: Order? = null
                for(order in orderList){
                    val items = order.products
                    Log.d("Item", "${items}")
                    for(item in items){
                        if(item.sellerID.equals(currentUser)){
                            if(storeOrder==null) {
                                storeOrder = Order(order.checkoutID, order.paymentID,order.paymentTimestamp,order.customerID,
                                    listOf(),0.0,0.0,0.0,order.orderStatus,order.paymentMethod)
                            }
                            Log.d("StoreOrder", "${storeOrder}")
                            storeOrder.products = storeOrder.products.plus(item)
                        }
                    }
                   if(storeOrder != null) {
                        Log.d("TEST", "TEST Store Order")
                        if(storeOrders.isEmpty()){
                            storeOrders = storeOrders.plus(storeOrder)
                        }else{
                            for(storeOrd in storeOrders){
                                if(storeOrd == storeOrder){
                                    storeOrder = null
                                    continue
                                }
                            }
                        }
                    }
                    storeOrder = null
                }
                _orders.value = storeOrders
            }
    }


}