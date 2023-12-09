package com.example.unshelf.view.marketplaceMain

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.R
import com.example.unshelf.model.entities.Product
//import com.example.unshelf.model.entities.sampleProduct
import com.example.unshelf.view.product.AdapterCartItem
import com.example.unshelf.view.product.CartItemData
import com.example.unshelf.view.product.product_main
import com.google.android.material.bottomnavigation.BottomNavigationView

class marketplaceMain : AppCompatActivity() {

    private lateinit var recyclerView_SO: RecyclerView
    private lateinit var adapter_SO: mm_selling_out_recyclerview
    private lateinit var dataList_SO: ArrayList<Product>

    private lateinit var recyclerView_BD: RecyclerView
    private lateinit var adapter_BD: mm_bundle_deals_recyclerview
    private lateinit var dataList_BD: ArrayList<Product>

    private lateinit var recyclerView_DG: RecyclerView
    private lateinit var adapter_DG: mm_discounted_goods_recyclerview
    private lateinit var dataList_DG: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace_main)

        //Selling Out RV
        recyclerView_SO = findViewById(R.id.rv_mm_so_container)
        dataList_SO = arrayListOf()
        //getDataSO()
        adapter_SO = mm_selling_out_recyclerview(dataList_SO)

        recyclerView_SO.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_SO.adapter = adapter_SO


        //Bundle Deals RV
        recyclerView_BD = findViewById(R.id.rv_mm_bd_container)
        dataList_BD = arrayListOf()
        //getDataBD()
        adapter_BD = mm_bundle_deals_recyclerview(dataList_BD)

        recyclerView_BD.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_BD.adapter = adapter_BD

        //Discounted Goods RV
        recyclerView_DG = findViewById(R.id.rv_mm_dg_container)
        dataList_DG = arrayListOf()
        //getDataDG()
        adapter_DG = mm_discounted_goods_recyclerview(dataList_BD)

        recyclerView_DG.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_DG.adapter = adapter_DG



        //EVENTLISTENER for each recyclerViews
        adapter_SO.setOnItemClickListener(object : mm_selling_out_recyclerview.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@marketplaceMain, product_main::class.java)
                startActivity(intent)
            }
        })

        adapter_BD.setOnItemClickListener(object : mm_bundle_deals_recyclerview.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@marketplaceMain, product_main::class.java)
                startActivity(intent)
            }
        })

        adapter_DG.setOnItemClickListener(object : mm_discounted_goods_recyclerview.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@marketplaceMain, product_main::class.java)
                startActivity(intent)
            }
        })



    }
/*
    private fun getDataSO() {
        val pabsManok: Product = sampleProduct(
            "Pabs lechon Manok",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val luckySalad: Product = sampleProduct(
            "Lucky Fruit Salad",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val topsShirmp: Product = Product(
            "Tops Eatery Shirmp",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val jethroPunko: Product = Product(
            "Jethro’s Pungko2x Chicharon Bulaklak",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val hernAmpalaya: Product = Product(
            "Hernah’s Karinderya Ampalaya w/ egg",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        dataList_SO.add(hernAmpalaya)
        dataList_SO.add(jethroPunko)
        dataList_SO.add(topsShirmp)
        dataList_SO.add(luckySalad)
        dataList_SO.add(pabsManok)
    }

    private fun getDataBD() {
        val pabsManok: Product = Product(
            "Amigo Spaghetti",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val luckySalad: Product = Product(
            "Oats + Milk Bundle",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val topsShirmp: Product = Product(
            "Golden Grains 2kg",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val jethroPunko: Product = Product(
            "Christmas Bundle",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val hernAmpalaya: Product = Product(
            "Mega Mackerel + Squid Bundle",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        dataList_BD.add(hernAmpalaya)
        dataList_BD.add(jethroPunko)
        dataList_BD.add(topsShirmp)
        dataList_BD.add(luckySalad)
        dataList_BD.add(pabsManok)
    }

    private fun getDataDG() {
        val pabsManok: Product = Product(
            "Amigo Spaghetti",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val luckySalad: Product = Product(
            "Oats + Milk Bundle",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val topsShirmp: Product = Product(
            "Golden Grains 2kg",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val jethroPunko: Product = Product(
            "Christmas Bundle",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        val hernAmpalaya: Product = Product(
            "Mega Mackerel + Squid Bundle",
            listOf(),
            "Sample Description",
            "Sample Description",
            "Sample Description",
            140,
            listOf(),
            "12-12-12",
            10,
            69
        )

        dataList_DG.add(hernAmpalaya)
        dataList_DG.add(jethroPunko)
        dataList_DG.add(topsShirmp)
        dataList_DG.add(luckySalad)
        dataList_DG.add(pabsManok)
    }

 */
}