package com.example.unshelf.view.AddressManager

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.R
import com.example.unshelf.helper.UI_Tester_Helper
import com.example.unshelf.view.RestaurantNearMe.MapActivity
import com.example.unshelf.view.adminApproval.VerificationS
import com.example.unshelf.view.marketplaceMain.marketplaceMain
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale

class UserAddress : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var currentMarkerPosition: LatLng
    private lateinit var gmap: GoogleMap
    private lateinit var tempMarker: Marker
    private lateinit var mapFragment: SupportMapFragment

    // map variables
    private var currentLatitude: Double? = null
    private var currentLongitude: Double? = null

    // textview
    private lateinit var specificAddress: TextView
    private lateinit var currentCity: TextView

    // Buttons
    private lateinit var btnConfirmLocation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address)
        initializeUI() // Initialize UI elements
        initializeMap() // Initialize Google Maps

        btnConfirmLocation.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun initializeUI() {
        // buttons
        btnConfirmLocation = findViewById(R.id.btnConfirmLocation)

        // textviews
        specificAddress = findViewById(R.id.tvSpecificAddress)
        currentCity = findViewById(R.id.tvCity)

        UI_Tester_Helper.UI_Test(this, btnConfirmLocation, MapActivity::class.java)
    }

    private fun addMapListeners() {
        mapFragment.getMapAsync { googleMap ->
            googleMap.setOnCameraIdleListener {
                val target = googleMap.cameraPosition.target
                addMarker(target)
            }

            googleMap.setOnCameraMoveListener {
                googleMap.clear() // Clear all markers when the camera is moved
            }
        }
    }

    private fun addMarker(markerPosition: LatLng) {
        val markerOptions = MarkerOptions().position(markerPosition)

        mapFragment.getMapAsync { googleMap ->
            if (::tempMarker.isInitialized) {
                tempMarker.remove()
            }
            tempMarker = googleMap.addMarker(markerOptions)!!

            // Store the current marker position
            currentMarkerPosition = markerPosition
            currentLatitude = markerPosition.latitude
            currentLongitude = markerPosition.longitude

            // Move the camera to the marker's position
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 18f))

            // Reverse geocoding to get address details
            val geocoder = Geocoder(this, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(currentLatitude!!, currentLongitude!!, 1)
                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        val address = addresses[0]
                        val province = address.locality?.replace("City", "")
                        val specificAddressText = address.getAddressLine(0)
                            ?.replace(", " + address.locality, "")
                            ?.replace(", " + address.countryName, "")
                            ?.replace(", " + address.postalCode, "")

                        // Set the text in TextViews
                        specificAddress.text =  specificAddressText
                        currentCity.text = address.locality // City
                    } else {
                        // Handle the case when no address is found
                        specificAddress.text = "Unknown"
                        currentCity.text = "Unknown"
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun initializeMap() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.user_add_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        addMapListeners() // Add map listeners after initializing the map
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        val location = LatLng(10.2945, 123.8811)
//        gmap.addMarker(MarkerOptions().position(location).title("CIT-U"))
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
    }
}
