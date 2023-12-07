package com.example.unshelf.view.BuyerBottomNav.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.unshelf.view.BuyerBottomNav.main.BuyerAppNavigation


class MainNavigationActivityBuyer : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            BuyerAppNavigation()
        }
    }
}
