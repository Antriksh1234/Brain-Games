package com.atandroidlabs.braingames20.WhatsTheWord

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.atandroidlabs.braingames20.R

class WhatsTheWordMainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_the_word_main)

        val playAloneButton: Button = findViewById(R.id.play_whats_the_word_alone)
        val playWithFriends: Button = findViewById(R.id.play_whats_the_word_with_friends)

        playAloneButton.setOnClickListener {
            val intent = Intent(applicationContext, WhatsTheWordGameActivity::class.java)
            intent.putExtra("alone", true)
            startActivity(intent)
        }

        playWithFriends.setOnClickListener {
            val intent = Intent(applicationContext, WhatsTheWordGameActivity::class.java)
            intent.putExtra("alone", false)
            startActivity(intent)
        }
    }
}