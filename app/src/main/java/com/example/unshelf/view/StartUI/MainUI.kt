package com.example.unshelf.view.StartUI

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.unshelf.R
import com.example.unshelf.controller.DataFetch.DataFetchRepository

class MainUI : AppCompatActivity() {
    val dataFetch = DataFetchRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMain = findViewById<Button>(R.id.btn_get_started)
        val btnLoading = findViewById<ProgressBar>(R.id.loading_bar)
        val spinner: Spinner = findViewById(R.id.spinnerAddress)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.city_options,
            R.layout.custom_spinner_item  // Use your custom layout here
        )
        adapter.setDropDownViewResource(R.layout.custom_spinner_item)  // Use your custom layout for the dropdown view as well
        spinner.adapter = adapter

        btnMain.setOnClickListener(View.OnClickListener {
            if(dataFetch.isLoading.value == false) {
                btnMain.visibility = View.VISIBLE
                btnLoading.visibility = View.GONE
            } else {
                btnMain.visibility = View.GONE
                btnLoading.visibility = View.VISIBLE
                val intent = Intent(this, MainMarketplace::class.java)
                this.startActivity(intent)
            }
        })
    }
}
