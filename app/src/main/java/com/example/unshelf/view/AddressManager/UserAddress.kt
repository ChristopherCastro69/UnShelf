package com.example.unshelf.view.AddressManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.unshelf.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserAddress : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gmap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.user_add_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        val location = LatLng(10.2945, 123.8811)
        gmap.addMarker(MarkerOptions().position(location).title("CIT-U"))
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
    }

}
