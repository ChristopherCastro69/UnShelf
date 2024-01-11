package com.example.unshelf.controller


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unshelf.model.entities.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object OrderController : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    var orderList = mutableStateOf(listOf<Order>())
    var isLoading = mutableStateOf(true)

    init {
        fetchOrder()
    }

    fun fetchOrder(){
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        CoroutineScope(Dispatchers.Main).launch {
            try {
                db.collection("orders").whereEqualTo("sellerID", currentUser)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            isLoading.value = false
                            return@addSnapshotListener
                        }
                        if (snapshot != null) {
                            orderList.value = snapshot.toObjects(Order::class.java)
                            val tempOrderList = snapshot.toObjects(Order::class.java)
                            var copyOrderList = snapshot.toObjects(Order::class.java)
                            var sortedValues: MutableList<Pair<Int, Long>> = mutableListOf()
                            var index = 0

                            for (order in tempOrderList) {
                                val convertedTimestamp: Long = order.paymentTimestamp!!.time
                                sortedValues.add(Pair(index, convertedTimestamp))
                                index++
                            }

                            sortedValues.sortByDescending {it.second}

                            index = 0


                            for ((sortedIndex, value) in sortedValues) {
                                copyOrderList[index] = tempOrderList.get(sortedIndex)
                                index++
                            }
                            isLoading.value = false
                            orderList.value = copyOrderList
                        }
                    }
            } catch (e: Exception) {
                isLoading.value = false
            }
        }

    }

    fun getreference(){

    }

}