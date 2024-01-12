package com.example.unshelf.view.RestaurantNearMe

import android.location.Location

import com.example.unshelf.view.RestaurantNearMe.clusters.ZoneClusterItem

data class MapState(
    val lastKnownLocation: Location?,
    val clusterItems: List<ZoneClusterItem>,
)
