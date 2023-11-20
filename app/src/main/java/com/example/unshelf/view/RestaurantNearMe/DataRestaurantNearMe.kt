package com.example.unshelf.view.RestaurantNearMe

data class DataRestaurantNearMe( // This is a data class, must belong to the model
    var thumbnail: Int,
    var star: Int,
    var restaurantName: String,
    var rating: String,
    var cuisine: String,
    var time: String
    )