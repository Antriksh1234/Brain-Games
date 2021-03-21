package com.atandroidlabs.braingames20.Sequence

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.atandroidlabs.braingames20.HelpActivity
import com.atandroidlabs.braingames20.HighScoreActivity
import com.atandroidlabs.braingames20.R

class SequenceMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequence_main)

        val start_btn = findViewById<Button>(R.id.start_sequence)
        val help_btn = findViewById<Button>(R.id.help_sequence)
        val highScore = findViewById<Button>(R.id.high_score_sequence)

        start_btn.setOnClickListener {
            val intent = Intent(applicationContext, SequenceGameActivity::class.java)
            startActivity(intent)
        }

        help_btn.setOnClickListener {
            val intent = Intent(applicationContext, HelpActivity::class.java)
            intent.putExtra("help of", R.string.sequence)
            startActivity(intent)
        }

        highScore.setOnClickListener {
            val intent = Intent(applicationContext, HighScoreActivity::class.java)
            intent.putExtra("table_name", "sequence")
            startActivity(intent)
        }
    }
}