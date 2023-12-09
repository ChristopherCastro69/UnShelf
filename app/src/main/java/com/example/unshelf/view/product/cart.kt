package com.example.unshelf.view.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.R
import com.example.unshelf.view.Wallet.CheckoutUI
import com.example.unshelf.view.marketplaceMain.marketplaceMain
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class cart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<CartItemData>

    lateinit var productNameList: Array<String>
    lateinit var priceList: Array<Double>
    lateinit var sellerNameList: Array<String>
    lateinit var variationList: Array<String>
    lateinit var qtyList: Array<Int>
    lateinit var productName: String
    private var price: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backBtn = findViewById<ImageView>(R.id.cart_backBtn)
        val cartBtn = findViewById<Button>(R.id.cart_checkout_btn)

        backBtn.setOnClickListener {
            val intent = Intent(this, marketplaceMain::class.java)
            startActivity(intent)
        }

        cartBtn.setOnClickListener {
            val intent = Intent(this, CheckoutUI::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.rvCart);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        dataList = arrayListOf<CartItemData>();
        fetchSpecificProductFromFirestore("1RpWybxUiNepjFTcJWvR")
    }

    private fun fetchSpecificProductFromFirestore(productId: String) {
        // Replace "Product" with the actual name of your Firestore collection
        val db = Firebase.firestore

        db.collection("Product").get()
            .addOnSuccessListener {documents ->
                for (document in documents) {
                    val sellerName = "SellerName" // Replace this with the actual field name
                    val productName = document.getString("Name") ?: ""
                    val quantity = document.getLong("quantity")?.toInt() ?: 0
                    val price = document.getDouble("price") ?: 0.00

                    // Create a CartItemData object and add it to dataList
                    val cartItemData = CartItemData(sellerName, productName, quantity, "sample", price)
                    dataList.add(cartItemData)
                }
                recyclerView.adapter = AdapterCartItem(dataList)
            }
            .addOnFailureListener {
                Log.d("ERROR:", "Failed to retrieve product data", it)
            }
    }

    private fun getData() {
        for (i in 0 until 10) {
            val data = CartItemData(
                productName,  // Use the retrieved product name
                "ASSD",
                2,
                "ASSD",
                price!!  // Use the retrieved price
            )
            dataList.add(data)
        }
        recyclerView.adapter = AdapterCartItem(dataList)
    }
}
