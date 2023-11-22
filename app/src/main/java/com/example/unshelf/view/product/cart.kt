package com.example.unshelf.view.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.R
import com.example.unshelf.view.RestaurantNearMe.AdapterRestaurantNearMe
import com.example.unshelf.view.RestaurantNearMe.DataRestaurantNearMe

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

        recyclerView = findViewById(R.id.rvCart);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        dataList = arrayListOf<CartItemData>();
        getData();
    }

    private fun getData() {
        for (i in 0 until 10) {
            val data = CartItemData(
                "Lucky's Fruits",
                "Fruit Salad",
                2,
                "Mango",
                260.00
            )
            dataList.add(data)
        }
        recyclerView.adapter = AdapterCartItem(dataList)
    }
}