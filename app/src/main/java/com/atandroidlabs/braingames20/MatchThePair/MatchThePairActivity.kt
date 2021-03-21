package com.atandroidlabs.braingames20.MatchThePair

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.atandroidlabs.braingames20.HelpActivity
import com.atandroidlabs.braingames20.R

class MatchThePairActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_the_pair)

        val start_btn = findViewById<Button>(R.id.start_match_the_pair)
        val help_btn = findViewById<Button>(R.id.help_match_the_pair)

        start_btn.setOnClickListener {
            val intent = Intent(applicationContext, MatchThePairGameActivity::class.java)
            startActivity(intent)
        }

        help_btn.setOnClickListener {
            val intent = Intent(applicationContext, HelpActivity::class.java)
            intent.putExtra("help of", R.string.match_the_pair)
            startActivity(intent)
        }
    }
}