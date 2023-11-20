package com.example.unshelf.view.SplashScreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.unshelf.MainActivity
import com.example.unshelf.R
import com.example.unshelf.R.id.splashscreen

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

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