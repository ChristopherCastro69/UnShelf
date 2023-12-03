package com.example.unshelf.helper

import android.app.Activity
import android.content.Context
import android.widget.Button
import android.widget.ImageView

class NavigationHelper {
    // Controlls the navigation of pages within the application

    companion object {
        @JvmStatic
        fun GoBack(context: Context, button: Button) {
            // closes the current activity and goes back to the previously opened activity
            button.setOnClickListener {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            }
        }
        @JvmStatic
        fun GoBack(context: Context, imageView: ImageView) {
            imageView.setOnClickListener {
                if (context is Activity) {
                    (context as Activity).finish()
                }
            }
        }


    }
}