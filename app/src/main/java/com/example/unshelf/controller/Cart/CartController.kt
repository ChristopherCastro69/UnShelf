package com.example.unshelf.controller.Cart

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unshelf.controller.DataFetch.DataFetchRepository
import com.example.unshelf.model.cart.CartItemModel
import com.example.unshelf.model.cart.CartModel
import com.example.unshelf.model.entities.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object CartController {
    val db = FirebaseFirestore.getInstance()
    var cartList: CartModel = CartModel()
    private val dataFetch = DataFetchRepository
    var storeMapped = mutableMapOf<String, MutableList<Product>>()
    var storeActiveState = mutableMapOf<String, MutableState<Boolean>>()
    var isLoading = mutableStateOf(true)

    init {
        fetchCart()
    }

    fun fetchCart() {
        CoroutineScope(Dispatchers.Main).launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            try {
                val cartRetrieve = db.collection("carts").document(currentUser!!.uid).get().await()
                if (cartRetrieve.exists()) {
                    cartList = cartRetrieve.toObject(CartModel::class.java)!!
                } else {
                    cartList = CartModel(currentUser.uid, emptyList())
                }
                isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addToCart(product: Product, source: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val item = CartItemModel(
                    product.productID,
                    product.quantity,
                )

                if (storeMapped.containsKey(product.storeID)) {
                    val productList = storeMapped[product.storeID]

                    if (productList != null) {
                        var exists = false
                        for (p in productList) {
                            if (p.productID == product.productID) {
                                if(source == "product") {
                                    p.quantity += product.quantity ?: 0
                                } else {
                                    p.quantity = product.quantity
                                }

                                exists = true
                                break
                            }
                        }

                        if (!exists) {
                            productList.add(product)
                        }
                    }
                } else {
                    storeMapped[product.storeID] = mutableListOf(product)
                }

                var exists = false
                for (i in cartList.itemList) {
                    if (i.productID == item.productID) {
                        if(source == "product") {
                            i.quantity = i.quantity!! + product.quantity ?: 0
                        } else {
                            i.quantity = product.quantity
                        }
                        exists = true
                        break
                    }
                }

                if (!exists) {
                    cartList.itemList = cartList.itemList.orEmpty() + item
                }

                db.collection("carts").document(currentUser!!.uid)
                    .set(cartList, SetOptions.merge())
                    .await()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun setStoreMapped() {
        for (item in cartList.itemList) {
            val p = dataFetch.mappedProducts.value[item.productID]

            if (p != null) {
                p.quantity = item.quantity ?: 0
                val pList = storeMapped.getOrPut(p.storeID) { mutableListOf() }
                pList.add(p)
            } else {
                Log.w("ProductCart", "Product with ID ${item.productID} not found")
            }
        }
    }

    fun getCart(): MutableMap<String, MutableList<Product>> {
        setStoreMapped()
        return storeMapped
    }

    fun setStoreState() {
        for((storeID, _) in storeMapped) {
            storeActiveState.getOrPut(storeID) { mutableStateOf(true) }
        }
    }

    fun clearCart() {
        cartList = CartModel()
        storeMapped.clear()
        storeActiveState.clear()
        isLoading.value = true
    }
}
