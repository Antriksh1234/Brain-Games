package com.atandroidlabs.braingames20

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database: SQLiteDatabase = applicationContext.openOrCreateDatabase("games", Context.MODE_PRIVATE, null)
        database.execSQL("CREATE TABLE IF NOT EXISTS sequence (name VARCHAR, score INTEGER)")
        database.execSQL("CREATE TABLE IF NOT EXISTS mental_maths (name VARCHAR, mode VARCHAR, score INTEGER)")

        val gamesList = ArrayList<Game>()
        gamesList.add(Game("Sequence", R.drawable.sequence_option))
        gamesList.add(Game("Match The Pair", R.drawable.matchthepair_option))
        gamesList.add(Game("Mental Maths", R.drawable.mental_maths))
        //gamesList.add(Game("Guess the Celeb", R.drawable.guesstheceleb_option))
        gamesList.add(Game("What's The Word?", R.drawable.guesstheword_option))
        gamesList.add(Game("Tic Tac Toe", R.drawable.tictactoe_option))

        val recyclerView: RecyclerView = findViewById(R.id.games_recycler)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = GameAdapter(applicationContext, gamesList)
    }
}