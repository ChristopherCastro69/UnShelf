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
import java.util.Date

object OrderController : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
    var orderList = mutableStateOf(listOf<Order>())
    var isLoading = mutableStateOf(true)

    fun fetchOrder(){
        CoroutineScope(Dispatchers.Default).launch {
            val orderFetch = db.collection("orders").whereEqualTo("sellerID", currentUser).get().await()
            orderList.value = orderFetch.toObjects(Order::class.java)
            val tempOrderList = orderFetch.toObjects(Order::class.java)
            var copyOrderList = orderList.value as MutableList<Order>
            var sortedValues: MutableList<Pair<Int, Long>> = mutableListOf()
            var index = 0

            for (order in tempOrderList) {
                val convertedTimestamp: Long = order.paymentTimestamp!!.time
                sortedValues.add(Pair(index, convertedTimestamp))
                index++
            }
//            for ((sortedIndex, value) in sortedValues) {
//                println("Before value: " + Date(value) + " orig: " + sortedIndex + " v: " + value)
//            }

            sortedValues.sortByDescending { it.second }

            index = 0

//            for(temp in tempOrderList) {
//                println("temp: " + temp.paymentTimestamp)
//            }

            for ((sortedIndex, value) in sortedValues) {
                copyOrderList[index] = tempOrderList.get(sortedIndex)
                index++
            }
            orderList.value = copyOrderList
        }

    }

}