package com.example.unshelf.controller.Wallet

import androidx.compose.runtime.mutableStateOf
import com.example.unshelf.controller.OrderController
import com.example.unshelf.model.entities.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object WalletController {
    var salesList = mutableStateOf(listOf<Order>())
    var totalBalance = mutableStateOf(0.0)
    fun getReceipts() {
        salesList.value = emptyList()
        totalBalance.value = 0.0
        var copySalesList = mutableStateOf(listOf<Order>())
        for(order in OrderController.orderList.value) {
            if(order.orderStatus.equals("completed")) {
                salesList.value = salesList.value + order
                copySalesList.value = copySalesList.value + order
            }
        }
        var sortedValues: MutableList<Pair<Int, Long>> = mutableListOf()
        var index = 0

        for (order in salesList.value) {
            val convertedTimestamp: Long = order.paymentTimestamp!!.time
            sortedValues.add(Pair(index, convertedTimestamp))
            index++
            totalBalance.value += order.netAmount
        }

        sortedValues.sortByDescending { it.second }

        index = 0

        for ((sortedIndex, sortedvalue) in sortedValues) {
            var order = salesList.value.get(index)
            order = copySalesList.value.get(sortedIndex)
            index++
        }
    }


}