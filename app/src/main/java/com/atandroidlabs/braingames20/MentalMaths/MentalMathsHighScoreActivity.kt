package com.atandroidlabs.braingames20.MentalMaths

import android.app.Activity
import android.os.Bundle
import android.os.RecoverySystem
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.atandroidlabs.braingames20.HighScoreAdapter
import com.atandroidlabs.braingames20.Player
import com.atandroidlabs.braingames20.R

class MentalMathsHighScoreActivity : Activity() {

    private val easyList: ArrayList<Player> = ArrayList()
    private val difficultList: ArrayList<Player> = ArrayList()
    private lateinit var easyAdapter: HighScoreAdapter
    private lateinit var difficultAdapter: HighScoreAdapter
    private lateinit var recyclerView: RecyclerView

    private fun getHighScoresEasy() {
        val sqLiteDatabase = this.openOrCreateDatabase("games", MODE_PRIVATE, null)
        val cursor = sqLiteDatabase.rawQuery(
            "SELECT * FROM  mental_maths WHERE mode = 'Easy'",
            null
        )
        val nameIndex = cursor.getColumnIndex("name")
        val scoreIndex = cursor.getColumnIndex("score")
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val user = cursor.getString(nameIndex)
                val scoreEasy = cursor.getInt(scoreIndex)
                easyList.add(Player(user, scoreEasy))
                cursor.moveToNext()
            }
        }
        cursor.close()
    }

    private fun getHighScoresDifficult() {
        val sqLiteDatabase = this.openOrCreateDatabase("games", MODE_PRIVATE, null)
        val cursor = sqLiteDatabase.rawQuery(
            "SELECT * FROM  mental_maths WHERE mode = 'Difficult'",
            null
        )
        val nameIndex = cursor.getColumnIndex("name")
        val scoreIndex = cursor.getColumnIndex("score")
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val user = cursor.getString(nameIndex)
                val scoreDifficult = cursor.getInt(scoreIndex)
                difficultList.add(Player(user, scoreDifficult))
                cursor.moveToNext()
            }
        }
        cursor.close()
    }

    fun changeList(view: View) {
        when (view.id) {
            R.id.easy_high_scores-> {
                recyclerView.adapter = easyAdapter
            }
            else -> {
                recyclerView.adapter = difficultAdapter
            }
        }

        //recyclerView.invalidate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_maths_high_score)

        val radioGroup: RadioGroup = findViewById(R.id.radio_grp)
        recyclerView = findViewById(R.id.high_score_recyclerView)

        getHighScoresEasy()
        getHighScoresDifficult()

        if (easyList.size == 0) {
            easyList.add(Player("-", 0))
        }
        if (difficultList.size == 0) {
            difficultList.add(Player("-", 0))
        }

        easyAdapter = HighScoreAdapter(applicationContext, easyList)
        difficultAdapter = HighScoreAdapter(applicationContext, difficultList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = easyAdapter
    }
}