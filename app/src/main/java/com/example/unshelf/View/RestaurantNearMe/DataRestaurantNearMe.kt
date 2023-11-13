package com.example.unshelf.View.RestaurantNearMe

import android.widget.ImageView

data class DataRestaurantNearMe(
    var thumbnail: Int,
    var star: Int,
    var restaurantName: String,
    var rating: String,
    var cuisine: String,
    var time: String
    )