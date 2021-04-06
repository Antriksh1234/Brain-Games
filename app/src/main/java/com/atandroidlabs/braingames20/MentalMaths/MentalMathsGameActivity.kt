package com.atandroidlabs.braingames20.MentalMaths

import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.R
import java.util.*

class MentalMathsGameActivity : AppCompatActivity() {

    var level: String? = null
    var timer: CountDownTimer? = null
    var scorecard: TextView? = null
    var timestamp:TextView? = null
    var question:TextView? = null
    private var right_or_wrong:TextView? = null
    private lateinit var options: Array<Button?>
    var random: Random? = null
    var no_of_correct = 0
    var no_of_questions:Int = 0
    private var correct_option:Int = 0
    private var res = 0
    var operand1:Int = 0
    var operand2:Int = 0

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

    private fun operate_on_easy_mode() {
        operand1 = random!!.nextInt(150)
        operand2 = random!!.nextInt(150)
        res = operand1 + operand2
        correct_option = random!!.nextInt(4)
        options[correct_option]?.text = res.toString() + ""
        val other1: String
        val other2: String
        val other3: String
        if (correct_option == 0) {
            other1 = (res + random!!.nextInt(5)).toString()
            other2 = (res + random!!.nextInt(5) + 5).toString()
            other3 = (res + 10).toString()
            options[1]?.text = other1
            options[2]?.text = other2
            options[3]?.text = other3
        } else if (correct_option == 1) {
            other1 = (res + 10).toString()
            other2 = (res + random!!.nextInt(5) + 5).toString()
            other3 = (res - 10).toString()
            options[0]?.text = other1
            options[2]?.text = other2
            options[3]?.text = other3
        } else if (correct_option == 2) {
            other1 = (res - 10).toString()
            other2 = "${res + random!!.nextInt(3) + 5}"
            other3 = "${res + 10}"
            options[0]?.text = other1
            options[1]?.text = other2
            options[3]?.text = other3
        } else {
            other1 = (res + random!!.nextInt(5)).toString()
            other2 = (res - 10).toString()
            other3 = "${res + 10}"
            options[0]?.text = other1
            options[1]?.text = other2
            options[2]?.text = other3
        }
        val questionText: String = "$operand1 + $operand2 = ?"
        question?.text = questionText
        timer!!.start()
    }

    private var high_score = 0

    private fun check_for_high_score_and_insert() {
        val database = this.openOrCreateDatabase("games", MODE_PRIVATE, null)
        val c = database.rawQuery("SELECT * FROM mental_maths", null)
        val scoreIndex = c.getColumnIndex("score")
        high_score = 0
        if (c.moveToFirst()) high_score = c.getInt(scoreIndex)
        if (no_of_correct >= high_score) {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.score_showing_dialog, null, false)
            val score_textView = view.findViewById<TextView>(R.id.score_show_alert_dialog)
            val nameText = view.findViewById<EditText>(R.id.high_scorer)
            score_textView.text = "Score: $no_of_correct"
            val save_button = view.findViewById<Button>(R.id.save_high_score)
            builder.setView(view)
            builder.setCancelable(false)
            val dialog: AlertDialog = builder.create()
            save_button.setOnClickListener {
                if (nameText.text.toString().trim().isEmpty()) {
                    Toast.makeText(
                        this@MentalMathsGameActivity,
                        "Enter a name!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    var name = nameText.text.toString().trim()
                    if (no_of_correct > high_score) {
                        database.execSQL("DELETE FROM mental_maths WHERE score = $high_score")
                    }
                    name = name.capitalize(Locale.ENGLISH)
                    database.execSQL("INSERT INTO mental_maths(name,mode,score) VALUES ('$name', '$level', $no_of_correct)")
                    //"INSERT INTO mental_maths" + "(name,mode,score) VALUES('" + name + "','" + level.toString() + "'," + no_of_correct.toString() + ")"
                    dialog.dismiss()
                    finish()
                }
            }
            dialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.your_score, null, false)
            val score_textView = view.findViewById<TextView>(R.id.show_usual_score)
            score_textView.text = "Your score: $no_of_correct"
            val ok_button = view.findViewById<Button>(R.id.ok_button_score)
            builder.setView(view)
            builder.setCancelable(false)
            val dialog = builder.create()
            ok_button.setOnClickListener {
                finish()
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun operate_on_difficult_mode() {
        var operation = ""
        val operation_select = random!!.nextInt(3)
        //0 for +, 1 for -, 2 for *(multiply)
        if (operation_select == 0) {
            operand1 = random!!.nextInt(500)
            operand2 = random!!.nextInt(500)
            res = operand1 + operand2
            operation = " + "
        } else if (operation_select == 1) {
            operand1 = random!!.nextInt(500) + 180
            operand2 = random!!.nextInt(100)
            res = operand1 - operand2
            operation = " - "
        } else {
            operand1 = random!!.nextInt(40) + 6
            operand2 = random!!.nextInt(30) + 5
            res = operand1 * operand2
            operation = " x "
        }
        correct_option = random!!.nextInt(4)
        options[correct_option]?.text = res.toString() + ""
        correct_option = random!!.nextInt(4)
        options[correct_option]?.text = res.toString() + ""
        val other1: String
        val other2: String
        val other3: String
        if (correct_option == 0) {
            other1 = "${res + random!!.nextInt(5) + 1}"
            other2 = "${res + random!!.nextInt(4) + 6}"
            other3 = "${res + 10}"
            options[1]?.text = other1
            options[2]?.text = other2
            options[3]?.text = other3
        } else if (correct_option == 1) {
            other1 = "${res + 10}"
            other3 = "${res + random!!.nextInt(5) + 5}"
            other2 = (res - 10).toString()
            options[0]?.text = other1
            options[2]?.text = other2
            options[3]?.text = other3
        } else if (correct_option == 2) {
            other1 = (res + 10).toString()
            other2 = (res + random!!.nextInt(3) + 2).toString()
            other3 = (res - 10).toString()
            options[0]?.text = other1
            options[1]?.text = other2
            options[3]?.text = other3
        } else {
            other2 = (res + random!!.nextInt(5) + 3).toString()
            other1 = (res - 10).toString()
            other3 = (res + 10).toString()
            options[0]?.text = other1
            options[1]?.text = other2
            options[2]?.text = other3
        }
        val questionText: String = "$operand1$operation$operand2 = ?"
        question?.text = questionText
        timer!!.start()
    }

    fun check_answer_of_maths(view: View) {
        right_or_wrong?.visibility = View.VISIBLE
        val selectedAnswer: Int
        val button = view as Button
        selectedAnswer = button.text.toString().toInt()
        if (selectedAnswer == res) {
            //Answer is correct increment the no_of_correct answers
            no_of_correct++
            right_or_wrong?.setText("Right!")
        } else {
            //Display that you were wrong with the answer, don't increment score
            right_or_wrong?.setText("Wrong!")
        }
        no_of_questions++
        val scoreCardText = "Score:\n$no_of_correct/$no_of_questions"
        scorecard!!.text = scoreCardText

        //Now setup the next question depending on level
        timestamp?.setText("Time left:\n0:05")
        if (level!!.contentEquals("Easy")) {
            if (no_of_questions == 20) {
                check_for_high_score_and_insert()
            } else {
                operate_on_easy_mode()
            }
        } else {
            //It is difficult mode no of questions to be played is 30
            if (no_of_questions == 30) {
                check_for_high_score_and_insert()
            } else {
                operate_on_difficult_mode()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mental_maths_game)

        scorecard = findViewById(R.id.mental_score_textView)
        timestamp = findViewById(R.id.timestamp)
        right_or_wrong = findViewById(R.id.right_or_wrong_mental)
        question = findViewById(R.id.question_mental_text)

        //Since question is not attempted yet no text is displayed

        //Since question is not attempted yet no text is displayed
        right_or_wrong?.visibility = View.INVISIBLE

        options = arrayOfNulls<Button?>(4)

        options[0] = findViewById(R.id.maths_option1)
        options[1] = findViewById(R.id.maths_option2)
        options[2] = findViewById(R.id.maths_option3)
        options[3] = findViewById(R.id.maths_option4)

        no_of_correct = 0.also { no_of_questions = it }
        random = Random()

        //Get which level has user selected

        //Get which level has user selected
        level = intent.getStringExtra("mode") + ""

        timer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft: String = (millisUntilFinished / 1000).toString()
                timestamp?.text = "Time left:\n0:0$timeLeft"
            }

            override fun onFinish() {
                timestamp?.text = "Time left:\n0:00"
                no_of_questions++
                scorecard?.text = "Score:\n$no_of_correct/$no_of_questions"
                if (level!!.contentEquals("Easy")) {
                    operate_on_easy_mode()
                } else {
                    operate_on_difficult_mode()
                }
            }
        }

        if (level!!.contentEquals("Easy")) {
            operate_on_easy_mode()
        } else {
            operate_on_difficult_mode()
        }

    }
}