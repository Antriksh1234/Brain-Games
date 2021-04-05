package com.atandroidlabs.braingames20

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : Activity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onBackPressed() {
        handler.removeCallbacks(runnable)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        runnable = Runnable{
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        handler.postDelayed(runnable, 1000)
    }
}