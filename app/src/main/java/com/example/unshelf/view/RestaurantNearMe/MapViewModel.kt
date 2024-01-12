package com.example.unshelf.view.RestaurantNearMe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.unshelf.view.RestaurantNearMe.clusters.ZoneClusterItem
import com.example.unshelf.view.RestaurantNearMe.clusters.ZoneClusterManager
import com.example.unshelf.view.RestaurantNearMe.clusters.calculateCameraViewPoints
import com.example.unshelf.view.RestaurantNearMe.clusters.getCenterOfPolygon

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.model.polygonOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(): ViewModel() {

    val state: MutableState<MapState> = mutableStateOf(
        MapState(
            lastKnownLocation = null,
            clusterItems = listOf(
                ZoneClusterItem(
                    id = "zone-1",
                    title = "Unshelf Store",
                    snippet = "North Reclamation Area, Cebu, Philippines",
                    polygonOptions = polygonOptions {
                        // Coordinates for CIT-U in Cebu, Philippines
                        add(LatLng(10.3080, 123.9180))
                        add(LatLng(10.3050, 123.9200))
                        add(LatLng(10.3030, 123.9120))
                        add(LatLng(10.3100, 123.9110))
                        fillColor(POLYGON_FILL_COLOR)
                    }
                ),
                ZoneClusterItem(
                    id = "zone-2",
                    title = "SaveMore Store",
                    snippet = "Maribago Street, Cebu, Philippines",
                    polygonOptions = polygonOptions {
                        // Coordinates for SM Seaside Cebu
                        add(LatLng(10.2725, 123.9883))
                        add(LatLng(10.2715, 123.9889))
                        add(LatLng(10.2700, 123.9865))
                        add(LatLng(10.2710, 123.9858))
                        fillColor(POLYGON_FILL_COLOR)
                    }
                ),
                ZoneClusterItem(
                    id = "zone-1",
                    title = "Wildcats Store",
                    snippet = "N. Bacalso Ave, Cebu City, Philippines",
                    polygonOptions = polygonOptions {
                        // Coordinates for CIT-U in Cebu, Philippines
                        add(LatLng(10.2945, 123.8811))  // Center coordinates for CIT-U
                        // Adjust the following coordinates to match the exact boundaries of CIT-U
                        add(LatLng(10.2955, 123.8821))
                        add(LatLng(10.2935, 123.8831))
                        add(LatLng(10.2925, 123.8801))
                        add(LatLng(10.2945, 123.8811))  // Close the polygon
                        fillColor(POLYGON_FILL_COLOR)
                    }
                ),
            )
        )
    )



    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    state.value = state.value.copy(
                        lastKnownLocation = task.result,
                    )
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }

    fun setupClusterManager(
        context: Context,
        map: GoogleMap,
    ): ZoneClusterManager {
        val clusterManager = ZoneClusterManager(context, map)
        clusterManager.addItems(state.value.clusterItems)
        return clusterManager
    }

    fun calculateZoneLatLngBounds(): LatLngBounds {
        // Get all the points from all the polygons and calculate the camera view that will show them all.
        val latLngs = state.value.clusterItems.map { it.polygonOptions }
                .map { it.points.map { LatLng(it.latitude, it.longitude) } }.flatten()
       return latLngs.calculateCameraViewPoints().getCenterOfPolygon()
    }



    companion object {
        private val POLYGON_FILL_COLOR = Color.parseColor("#ABF44336")
    }
}