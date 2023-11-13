package com.example.unshelf.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.unshelf.Controller.NavigationController
import com.example.unshelf.R

class RestaurantsNearMe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_near_me)

        val ivBackBtn = findViewById<ImageView>(R.id.ivBackBtn);

        // CONTROLLER
        NavigationController.GoBack(this, ivBackBtn);
    }
}