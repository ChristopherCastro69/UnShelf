package com.example.unshelf.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Button

class UI_Tester_Controller { // Please update class diagram and add this class as controller
    // Facilitates the testing of the user interfaces created in the app
    companion object {
        @JvmStatic
        fun <T : Activity>  UI_Test(context: Context, button: Button, destinationClass: Class<T>) {
            button.setOnClickListener {
                val intent = Intent(context, destinationClass)
                context.startActivity(intent)
            }
        }
    }
}
