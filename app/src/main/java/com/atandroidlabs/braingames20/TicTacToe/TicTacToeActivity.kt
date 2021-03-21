package com.atandroidlabs.braingames20.TicTacToe

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.atandroidlabs.braingames20.HelpActivity
import com.atandroidlabs.braingames20.R

class TicTacToeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        val start_btn = findViewById<Button>(R.id.start_tic_tac_toe)
        start_btn.setOnClickListener {
            val intent = Intent(applicationContext, TicTacToeGameActivity::class.java)
            startActivity(intent)
        }

        val help_btn = findViewById<Button>(R.id.help_tic_tac_toe)
        help_btn.setOnClickListener {
            val intent = Intent(this@TicTacToeActivity, HelpActivity::class.java)
            intent.putExtra("help of", R.string.tic_tac_toe)
            startActivity(intent)
        }
    }
}