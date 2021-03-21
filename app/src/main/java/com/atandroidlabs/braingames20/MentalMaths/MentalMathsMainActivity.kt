package com.atandroidlabs.braingames20.MentalMaths

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.atandroidlabs.braingames20.HelpActivity
import com.atandroidlabs.braingames20.R

class MentalMathsMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_maths_main)

        val startButton = findViewById<Button>(R.id.start_mental_maths)
        val highScoreButton = findViewById<Button>(R.id.high_score_mental_maths)
        val helpButton = findViewById<Button>(R.id.help_mental_maths)

        startButton.setOnClickListener {
            val intent = Intent(applicationContext, MentalMathsMode::class.java)
            startActivity(intent)
        }

        highScoreButton.setOnClickListener {
            val intent = Intent(applicationContext, MentalMathsHighScoreActivity::class.java)
            startActivity(intent)
        }

        helpButton.setOnClickListener {
            val intent = Intent(applicationContext, HelpActivity::class.java)
            intent.putExtra("help of", R.string.mental_maths)
            startActivity(intent)
        }
    }
}