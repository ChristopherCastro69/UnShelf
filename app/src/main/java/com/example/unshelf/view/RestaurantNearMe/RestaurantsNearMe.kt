package com.example.unshelf.view.RestaurantNearMe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unshelf.helper.NavigationHelper
import com.example.unshelf.R

class RestaurantsNearMe : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var restaurantNearMeList: ArrayList<DataRestaurantNearMe>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_near_me)

        val ivBackBtn = findViewById<ImageView>(R.id.ivBackBtn);

        // CONTROLLER
        NavigationHelper.GoBack(this, ivBackBtn);

        // placeholder recyclerview
        recyclerView = findViewById(R.id.rvPopularRestaurant);
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);

        restaurantNearMeList = arrayListOf<DataRestaurantNearMe>();
        getData();
    }

    private fun getData() {
        for (i in 0 until 10) {
            val restaurant = DataRestaurantNearMe(
                R.drawable.fruit_salad_img,
                R.drawable.star_solid,
                "Jethro's Salad",
                "4.5",
                "Filipino",
                "25 mins"
            )
            restaurantNearMeList.add(restaurant)
        }
        recyclerView.adapter = AdapterRestaurantNearMe(restaurantNearMeList)
    }

}