package com.atandroidlabs.braingames20

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HighScoreActivity : Activity() {

    private lateinit var tableName: String
    private val highScoreList: ArrayList<Player> = ArrayList()
    private lateinit var adapter: HighScoreAdapter

    private fun getHighScores() {
        val sqLiteDatabase = this.openOrCreateDatabase("games", MODE_PRIVATE, null)
        val cursor = sqLiteDatabase.rawQuery("SELECT * FROM $tableName", null)
        cursor.moveToFirst()
        val nameIndex = cursor.getColumnIndex("name")
        val scoreIndex = cursor.getColumnIndex("score")
        while (!cursor.isAfterLast) {
            val name = cursor.getString(nameIndex)
            val score = cursor.getInt(scoreIndex)
            highScoreList.add(Player(name, score))
            cursor.moveToNext()
        }
        cursor.close()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        val highScoreRCV = findViewById<RecyclerView>(R.id.high_score_rcv)

        tableName = intent.getStringExtra("table_name")!!

        //fill the High Scores' list from the database
        getHighScores()

        if (highScoreList.size == 0)
            highScoreList.add(Player("-", 0))

        adapter = HighScoreAdapter(applicationContext, highScoreList)
        highScoreRCV.layoutManager = LinearLayoutManager(this)
        highScoreRCV.adapter = adapter
    }
}