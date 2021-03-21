package com.atandroidlabs.braingames20.GuessTheCeleb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.HelpActivity
import com.atandroidlabs.braingames20.HighScoreActivity
import com.atandroidlabs.braingames20.R

class GuessTheCelebMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_celeb_main)

        val start_btn = findViewById<Button>(R.id.start_game_celeb)
        val help_btn = findViewById<Button>(R.id.help_btn_celeb)
        val highScore_btn = findViewById<Button>(R.id.high_scores_celeb)

        start_btn.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(
                this@GuessTheCelebMainActivity,
                GuessTheCelebGameActivity::class.java
            )
            startActivity(intent)
        })

        help_btn.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(
                this@GuessTheCelebMainActivity,
                HelpActivity::class.java
            )
            intent.putExtra("help of", R.string.guess_the_celeb)
            startActivity(intent)
        })

        highScore_btn.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, HighScoreActivity::class.java)
            intent.putExtra("table_name", "celeb")
            startActivity(intent)
        })
    }
}