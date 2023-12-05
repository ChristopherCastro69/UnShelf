package com.example.unshelf.view.SellerBottomNav.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.unshelf.view.SellerBottomNav.main.AppNavigation


class MainNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AppNavigation()
        }
    }
}