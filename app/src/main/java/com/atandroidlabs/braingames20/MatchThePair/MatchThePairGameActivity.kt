package com.atandroidlabs.braingames20.MatchThePair

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.R
import java.util.*

class MatchThePairGameActivity : AppCompatActivity() {

    private lateinit var occupied: BooleanArray
    private var currentCard = -1
    private val photo_id = intArrayOf(
        R.drawable.food1,
        R.drawable.food2,
        R.drawable.food3,
        R.drawable.food4,
        R.drawable.food5,
        R.drawable.food6,
        R.drawable.food7,
        R.drawable.food8,
        R.drawable.food9,
        R.drawable.food10,
        R.drawable.food11,
        R.drawable.food12,
        R.drawable.food13,
        R.drawable.food14,
        R.drawable.food15,
        R.drawable.food16,
        R.drawable.food17,
        R.drawable.food18
    )
    private var player1_score = 0;
    private var player2_score:Int = 0
    private var player1_textView: TextView? = null
    private var player2_textView:TextView? = null
    private var turn_textView:TextView? = null
    private var images_position = IntArray(36)
    private var player1_chance = true
    private var map = HashMap<Int, Int>()
    private var flipped_position = -1
    private var imageViews = arrayOfNulls<ImageView>(36)
    private var gone = 0

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

    private fun setUpImages() {
        val random = Random()
        for (i in photo_id.indices) {
            //Getting first position for image
            var num1 = random.nextInt(36) //0 to 35
            while (occupied[num1]) {
                num1 = random.nextInt(36)
            }
            occupied[num1] = true
            images_position[num1] = photo_id[i]

            //Getting second position for image
            var num2 = random.nextInt(36) //0 to 35
            while (occupied[num2]) {
                num2 = random.nextInt(36)
            }
            occupied[num2] = true
            images_position[num2] = photo_id[i]
            map[num1] = num2
            map[num2] = num1
        }
    }

    private fun init_imageViews() {
        imageViews[0] = findViewById(R.id.img1)
        imageViews[1] = findViewById(R.id.img2)
        imageViews[2] = findViewById(R.id.img3)
        imageViews[3] = findViewById(R.id.img4)
        imageViews[4] = findViewById(R.id.img5)
        imageViews[5] = findViewById(R.id.img6)
        imageViews[6] = findViewById(R.id.img7)
        imageViews[7] = findViewById(R.id.img8)
        imageViews[8] = findViewById(R.id.img9)
        imageViews[9] = findViewById(R.id.img10)
        imageViews[10] = findViewById(R.id.img11)
        imageViews[11] = findViewById(R.id.img12)
        imageViews[12] = findViewById(R.id.img13)
        imageViews[13] = findViewById(R.id.img14)
        imageViews[14] = findViewById(R.id.img15)
        imageViews[15] = findViewById(R.id.img16)
        imageViews[16] = findViewById(R.id.img17)
        imageViews[17] = findViewById(R.id.img18)
        imageViews[18] = findViewById(R.id.img19)
        imageViews[19] = findViewById(R.id.img20)
        imageViews[20] = findViewById(R.id.img21)
        imageViews[21] = findViewById(R.id.img22)
        imageViews[22] = findViewById(R.id.img23)
        imageViews[23] = findViewById(R.id.img24)
        imageViews[24] = findViewById(R.id.img25)
        imageViews[25] = findViewById(R.id.img26)
        imageViews[26] = findViewById(R.id.img27)
        imageViews[27] = findViewById(R.id.img28)
        imageViews[28] = findViewById(R.id.img29)
        imageViews[29] = findViewById(R.id.img30)
        imageViews[30] = findViewById(R.id.img31)
        imageViews[31] = findViewById(R.id.img32)
        imageViews[32] = findViewById(R.id.img33)
        imageViews[33] = findViewById(R.id.img34)
        imageViews[34] = findViewById(R.id.img35)
        imageViews[35] = findViewById(R.id.img36)
    }

    private fun checkForPair(imageView: ImageView, pos: Int) {
        try {
            if (flipped_position != -1) {
                if (pos == map[flipped_position]) {
                    if (player1_chance) {
                        player1_score++
                        player1_textView!!.text = "$player1_score"
                    } else {
                        player2_score++
                        player2_textView!!.text = "$player2_score"
                    }
                    imageView.animate().translationYBy(2500f).duration = 500
                    imageViews[flipped_position]!!.animate().translationYBy(2500f).duration = 500
                    gone += 2
                    if (gone == 36) {
                        //All images are flipped and gone
                        createAlertDialogForShowingScore()
                    }
                } else {
                    player1_chance = !player1_chance
                    if (player1_chance) {
                        turn_textView!!.text = "Player 1's turn"
                    } else {
                        turn_textView!!.text = "Player 2's turn"
                    }
                    imageView.setImageResource(R.drawable.pair_background)
                    imageViews[flipped_position]!!.setImageResource(R.drawable.pair_background)
                    imageView.animate().rotationYBy(180f).duration = 500
                    imageViews[flipped_position]!!.animate().rotationYBy(180f).duration = 500
                }
                flipped_position = -1
            } else {
                flipped_position = pos
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun flipImage(view: View) {
        val imageView = view as ImageView
        val pos = imageView.tag.toString().toInt() - 1
        if (pos == currentCard) {
            return
        }
        currentCard = pos
        if (pos != flipped_position) {
            imageView.animate().rotationYBy(180f).duration = 300
            imageView.setImageResource(images_position[pos])
            val handler = Handler()
            handler.postDelayed({ checkForPair(imageView, pos) }, 1000)
        }
    }

    private fun createAlertDialogForShowingScore() {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.your_score, null, false)
        val your_score_textView = view.findViewById<TextView>(R.id.show_usual_score)
        val ok_button = view.findViewById<Button>(R.id.ok_button_score)
        var finalVerdict = ""
        finalVerdict = if (player1_score > player2_score) {
            "Player 1 won the game!\t$player1_score:$player2_score"
        } else if (player2_score > player1_score) {
            "Player 2 won the game!\t$player2_score:$player1_score"
        } else {
            "Its a tie!\t$player1_score:$player2_score"
        }
        your_score_textView.text = finalVerdict
        builder.setView(view)
        val dialog = builder.create()
        ok_button.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_the_pair_game)

        player1_textView = findViewById(R.id.player1_match)
        player2_textView = findViewById<TextView>(R.id.player2_match)
        turn_textView = findViewById<TextView>(R.id.turn_match)
        init_imageViews()

        occupied = BooleanArray(36)
        player1_score = 0.also { player2_score = it }

        setUpImages()
    }
}