package com.example.interntaskapp

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startCrazyAnimations()

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                startActivity(Intent(this, SignInActivity::class.java))
            }
            finish()
        }, 3000) // Extended to 3 seconds for more fun
    }
    
    private fun startCrazyAnimations() {
        val emojiIds = arrayOf(
            R.id.emoji1, R.id.emoji2, R.id.emoji3, R.id.emoji4, R.id.emoji5,
            R.id.emoji6, R.id.emoji7, R.id.emoji8, R.id.emoji9, R.id.emoji10,
            R.id.emoji11, R.id.emoji12, R.id.emoji13, R.id.emoji14, R.id.emoji15,
            R.id.emoji16, R.id.emoji17, R.id.emoji18, R.id.emoji19, R.id.emoji20
        )
        
        emojiIds.forEachIndexed { index, emojiId ->
            val emoji = findViewById<TextView>(emojiId)
            
            when (index % 5) {
                0 -> {
                    // Bounce animation
                    ObjectAnimator.ofFloat(emoji, "translationY", 0f, -50f, 0f).apply {
                        duration = (800 + (index * 50)).toLong()
                        repeatCount = ObjectAnimator.INFINITE
                        interpolator = BounceInterpolator()
                        start()
                    }
                }
                1 -> {
                    // Rotation animation
                    ObjectAnimator.ofFloat(emoji, "rotation", 0f, 360f).apply {
                        duration = (1000 + (index * 100)).toLong()
                        repeatCount = ObjectAnimator.INFINITE
                        interpolator = AccelerateDecelerateInterpolator()
                        start()
                    }
                }
                2 -> {
                    // Scale animation
                    ObjectAnimator.ofFloat(emoji, "scaleX", 1f, 1.5f, 1f).apply {
                        duration = (600 + (index * 80)).toLong()
                        repeatCount = ObjectAnimator.INFINITE
                        start()
                    }
                    ObjectAnimator.ofFloat(emoji, "scaleY", 1f, 1.5f, 1f).apply {
                        duration = (600 + (index * 80)).toLong()
                        repeatCount = ObjectAnimator.INFINITE
                        start()
                    }
                }
                3 -> {
                    // Alpha animation
                    ObjectAnimator.ofFloat(emoji, "alpha", 1f, 0.2f, 1f).apply {
                        duration = (900 + (index * 60)).toLong()
                        repeatCount = ObjectAnimator.INFINITE
                        start()
                    }
                }
                4 -> {
                    // Horizontal movement
                    ObjectAnimator.ofFloat(emoji, "translationX", 0f, 30f, -30f, 0f).apply {
                        duration = (1200 + (index * 40)).toLong()
                        repeatCount = ObjectAnimator.INFINITE
                        interpolator = AccelerateDecelerateInterpolator()
                        start()
                    }
                }
            }
        }
    }
}