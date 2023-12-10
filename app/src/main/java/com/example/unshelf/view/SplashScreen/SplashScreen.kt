package com.example.unshelf.view.SplashScreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.example.unshelf.view.MainActivity
import com.example.unshelf.R
import com.example.unshelf.R.id.splashscreen
import com.example.unshelf.controller.DataFetch.DataFetchController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashScreen : AppCompatActivity() {
    private lateinit var dataFetchController: DataFetchController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        firebaseAuth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Debug", "signInAnonymously:success")
                    val user = firebaseAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Debug", "signInAnonymously:failure", task.exception)
                }
            }
        setContentView(R.layout.activity_splash_screen)
//        dataFetchController = ViewModelProvider(this).get(DataFetchController::class.java)

        val splashScreen = findViewById<ImageView>(splashscreen)

        val alphaAnimator = ObjectAnimator.ofFloat(splashScreen, "alpha", 0f, 1f)
        alphaAnimator.duration = 1000

        alphaAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val i = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        })

        alphaAnimator.start()
    }
}