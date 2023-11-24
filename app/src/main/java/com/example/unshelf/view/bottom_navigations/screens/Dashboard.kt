package com.example.unshelf.view.bottom_navigations.screens

import android.text.Layout.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

@Composable
fun Dashboard(){
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center){
        Text(
            text = "Dashboard Screen",
            fontFamily = FontFamily.Serif,
            fontSize = 22.sp)
    }
}