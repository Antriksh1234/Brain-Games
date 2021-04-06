package com.atandroidlabs.braingames20.Sequence

import android.app.Activity
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.R
import java.util.*

class SequenceGameActivity : AppCompatActivity() {

    private var given_textView: TextView? = null;
    private var answer_textView:TextView? = null;
    private  var score_textView:TextView? = null
    private lateinit var backButton: Button
    private lateinit var clearButton: Button
    var timer: CountDownTimer? = null
    lateinit var b: Array<Button?>
    var high_score = 0
    var given_text: String? = null;
    var answer_text:String? = null
    var scores = 0
    var random: Random? = null

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Want to exit?")
            .setMessage("Are you sure you want to exit the game?")
            .setNegativeButton("No", null)
            .setPositiveButton(
                "Yes"
            ) { _, _ -> finish() }
            .show()
    }

    fun addString(view: View) {
        val button = view as Button
        val tag = button.tag.toString()
        answer_text += tag
        answer_textView?.text = answer_text
    }

    fun finalize_answer(view: View?) {
        if (answer_text == given_text) {
            scores++
            given_text = ""
            for (i in 0 until scores + 2) {
                given_text = given_text + random!!.nextInt(10).toString() + ""
            }
            given_textView!!.text = given_text
            answer_text = ""
            answer_textView?.text = answer_text
            val scoreText = "Score: $scores"
            score_textView?.text = scoreText
            disableAllButton()
            timer!!.start()
        } else {
            val database: SQLiteDatabase = this@SequenceGameActivity.openOrCreateDatabase(
                "games",
                MODE_PRIVATE,
                null
            )
            val c = database.rawQuery("SELECT * FROM sequence", null)
            val scoreIndex = c.getColumnIndex("score")
            high_score = if (c.moveToFirst()) {
                c.getInt(scoreIndex)
            } else 0
            val builder: AlertDialog.Builder
            val dialog: AlertDialog
            if (scores >= high_score) {
                builder = AlertDialog.Builder(this@SequenceGameActivity)
                val alert_dialog_view =
                    layoutInflater.inflate(R.layout.score_showing_dialog, null, false)
                val save_high_score_button =
                    alert_dialog_view.findViewById<Button>(R.id.save_high_score)
                val nameText = alert_dialog_view.findViewById<EditText>(R.id.high_scorer)
                val score_show =
                    alert_dialog_view.findViewById<TextView>(R.id.score_show_alert_dialog)
                score_show.text = "Score: $scores"
                builder.setView(alert_dialog_view)
                builder.setCancelable(false)
                dialog = builder.create()
                save_high_score_button.setOnClickListener {
                    val name = nameText.text.toString()
                    if (name.contentEquals("")) {
                        Toast.makeText(
                            this@SequenceGameActivity,
                            "Enter a name!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        if (scores > high_score) {
                            database.execSQL("DELETE FROM sequence WHERE score = $high_score")
                        }
                        database.execSQL("INSERT INTO  sequence(name, score) VALUES('$name',$scores)")
                    }
                    dialog.dismiss()
                    finish()
                    Toast.makeText(
                        this@SequenceGameActivity,
                        "High Score added",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                builder = AlertDialog.Builder(this@SequenceGameActivity)
                val alert_dialog_view = layoutInflater.inflate(R.layout.your_score, null, false)
                val ok_button = alert_dialog_view.findViewById<Button>(R.id.ok_button_score)
                val score_show = alert_dialog_view.findViewById<TextView>(R.id.show_usual_score)
                score_show.text = "Your score: $scores"
                builder.setView(alert_dialog_view)
                builder.setCancelable(false)
                dialog = builder.create()
                ok_button.setOnClickListener {
                    dialog.dismiss()
                    finish()
                }
            }
            dialog.show()
        }
    }

    fun backspace(view: View?) {
        if (answer_text!!.isNotEmpty()) {
            answer_text = answer_text!!.substring(0, answer_text!!.length - 1)
            answer_textView?.setText(answer_text)
        }
    }

    fun clearTheText(view: View?) {
        answer_text = ""
        answer_textView?.setText(answer_text)
    }

    private fun disableAllButton() {
        for (i in 0..9) {
            b[i]?.isEnabled = false
        }
        clearButton.isEnabled = false
        backButton.isEnabled = false
    }

    private fun enableAllButton() {
        for (i in 0..9) {
            b[i]?.isEnabled = true
        }
        clearButton.isEnabled = true
        backButton.isEnabled = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequnce_game)

        given_textView = findViewById(R.id.sequence_text)
        answer_textView = findViewById(R.id.answer_textView)
        score_textView = findViewById(R.id.sequence_score_textView)
        backButton = findViewById(R.id.button1_back)
        clearButton = findViewById(R.id.button1_clear)
        actionBar?.hide()

        b = arrayOfNulls<Button>(10)
        b[0] = findViewById(R.id.button_0)
        b[1] = findViewById(R.id.button_1)
        b[2] = findViewById(R.id.button_2)
        b[3] = findViewById(R.id.button_3)
        b[4] = findViewById(R.id.button_4)
        b[5] = findViewById(R.id.button_5)
        b[6] = findViewById(R.id.button_6)
        b[7] = findViewById(R.id.button_7)
        b[8] = findViewById(R.id.button_8)
        b[9] = findViewById(R.id.button_9)

        random = Random()

        given_text = ""
        given_text = "${random!!.nextInt(100) + 100}"
        given_textView?.text = given_text
        answer_text = ""

        disableAllButton()

        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //let the clock run
            }

            override fun onFinish() {
                enableAllButton()
                var encrypted_text = ""
                for (i in 0 until given_text!!.length) encrypted_text += "X"
//                given_textView.setText(encrypted_text)
                given_textView?.text = encrypted_text
            }
        }

        (timer as CountDownTimer).start()
    }
}