package com.atandroidlabs.braingames20

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler

class SplashScreenActivity : Activity() {

    private lateinit var timer: CountDownTimer

    override fun onBackPressed() {
        timer.cancel()
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        timer = object : CountDownTimer(1500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //Wait for 1.5 seconds
            }

            override fun onFinish() {
                val intent: Intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        timer.start()
    }
}