package uk.ac.tees.mad.W9641667

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.splash)
        val logoImageView = findViewById<ImageView>(R.id.splashImage)
        logoImageView.startAnimation(logoAnimation)

        // Handler to start the MainActivity and close this Splash-Screen after 2 seconds.
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2500)
    }
}