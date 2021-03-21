package com.atandroidlabs.braingames20.MentalMaths

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.R

class MentalMathsMode : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_maths_mode)

        val easyButton = findViewById<Button>(R.id.play_easy_mental_maths)
        val difficultButton = findViewById<Button>(R.id.play_difficult_mental_maths)

        easyButton.setOnClickListener {
            val intent = Intent(this, MentalMathsGameActivity::class.java)
            intent.putExtra("mode", "Easy")
            startActivity(intent)
        }

        difficultButton.setOnClickListener {
            val intent = Intent(this, MentalMathsGameActivity::class.java)
            intent.putExtra("mode", "Difficult")
            startActivity(intent)
        }
    }
}