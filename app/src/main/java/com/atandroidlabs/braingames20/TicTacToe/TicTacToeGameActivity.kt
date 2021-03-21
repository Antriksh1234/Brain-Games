package com.atandroidlabs.braingames20.TicTacToe

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.R

class TicTacToeGameActivity : AppCompatActivity() {

    // 0 for cat 1 for puppy 2 for empty
    var gameState = intArrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    var winningPositions = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )
    var playerchance = 0
    var someoneWon = false
    var gameActive = true

    override fun onBackPressed() {
        android.app.AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Want to exit?")
            .setMessage("Are you sure you want to exit the game?")
            .setNegativeButton("No", null)
            .setPositiveButton(
                "Yes"
            ) { _, _ -> finish() }
            .show()
    }

    fun dropIn(view: View) {
        val counter = view as ImageView
        val tappedCounter = counter.tag.toString().toInt()
        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = playerchance
            counter.translationY = -2000f
            if (playerchance == 0) {
                counter.setImageResource(R.drawable.cat)
                counter.animate().translationYBy(2000f).duration = 100
                playerchance = 1
            } else {
                counter.setImageResource(R.drawable.puppy)
                counter.animate().translationYBy(2000f).duration = 100
                playerchance = 0
            }
            for (winningPosition in winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    //Someone has won
                    gameActive = false
                    someoneWon = true
                    var winner = ""
                    winner = if (playerchance == 1) {
                        "Cat"
                    } else "Puppy"
                    val Won = findViewById<View>(R.id.hasWontextView) as TextView
                    Won.text = "$winner has won!"
                    val PlayAgain = findViewById<View>(R.id.playAgainButton) as Button
                    PlayAgain.visibility = View.VISIBLE
                    Won.visibility = View.VISIBLE
                }
            } //end of winning position loop
            if (!someoneWon) {
                var allFilledAndDraw = false
                for (i in gameState.indices) {
                    if (gameState[i] != 2) allFilledAndDraw = true else {
                        allFilledAndDraw = false
                        break
                    }
                } //end of checking of all filled or not
                if (allFilledAndDraw) {
                    gameActive = false
                    val winner = ""
                    val Won = findViewById<View>(R.id.hasWontextView) as TextView
                    Won.text = "Match Drawn!"
                    val PlayAgain = findViewById<View>(R.id.playAgainButton) as Button
                    PlayAgain.visibility = View.VISIBLE
                    Won.visibility = View.VISIBLE
                }
            } //end of inner if(solves triangular bug)
        } //end of if condition which checks whether a place is empty in tic tac toe board or anyone has won due to which game is not active anymore
    } //end of dropIn()


    //when play again is pressed
    fun playOnceMore(view: View?) {
        val Won = findViewById<View>(R.id.hasWontextView) as TextView
        Won.text = ""
        val PlayAgain = findViewById<View>(R.id.playAgainButton) as Button
        PlayAgain.visibility = View.INVISIBLE
        Won.visibility = View.INVISIBLE
        Log.i("Info", "Error after this")
        val gridLayout: androidx.gridlayout.widget.GridLayout = findViewById<View>(R.id.gridlayout) as androidx.gridlayout.widget.GridLayout
        for (i in 0 until gridLayout.getChildCount()) {
            val counter = gridLayout.getChildAt(i) as ImageView
            counter.setImageDrawable(null)
        }
        gameActive = true
        someoneWon = false
        for (i in gameState.indices) gameState[i] = 2
        playerchance = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe_game)


    }
}