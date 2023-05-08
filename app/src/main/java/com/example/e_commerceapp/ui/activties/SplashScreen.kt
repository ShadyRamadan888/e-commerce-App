package com.example.e_commerceapp.ui.activties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.e_commerceapp.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(
            Runnable {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }, 3000
        )
    }
}