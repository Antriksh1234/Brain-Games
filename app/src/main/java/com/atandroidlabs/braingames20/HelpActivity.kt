package com.atandroidlabs.braingames20

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val instructions = findViewById<TextView>(R.id.instrcutions)
        val instruction_res_id = intent.getIntExtra("help of", R.string.app_name)
        instructions.setText(instruction_res_id)
    }
}