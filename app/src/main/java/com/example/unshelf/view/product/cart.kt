package com.example.unshelf.view.product

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.R
import com.example.unshelf.view.RestaurantNearMe.AdapterRestaurantNearMe
import com.example.unshelf.view.RestaurantNearMe.DataRestaurantNearMe
import com.example.unshelf.view.marketplaceMain.marketplaceMain

class cart : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<CartItemData>

    lateinit var productNameList: Array<String>
    lateinit var priceList: Array<Double>
    lateinit var sellerNameList: Array<String>
    lateinit var variationList: Array<String>
    lateinit var qtyList: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backBtn = findViewById<ImageView>(R.id.cart_backBtn)
        val cartBtn = findViewById<Button>(R.id.cart_checkout_btn)
        
        backBtn.setOnClickListener {
            val intent = Intent(this, marketplaceMain::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.rvCart);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        dataList = arrayListOf<CartItemData>();
        getData();
    }

    private fun getData() {
        for (i in 0 until 10) {
            val data = CartItemData(
                "Sample",
                "ASSD",
                2,
                "ASSD",
                260.00
            )
            dataList.add(data)
        }
        recyclerView.adapter = AdapterCartItem(dataList)
    }
}