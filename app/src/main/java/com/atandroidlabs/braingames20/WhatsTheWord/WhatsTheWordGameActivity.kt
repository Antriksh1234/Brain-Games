package com.atandroidlabs.braingames20.WhatsTheWord

import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.atandroidlabs.braingames20.R
import java.util.*

class WhatsTheWordGameActivity : AppCompatActivity() {

    private var aloneMode: Boolean = true
    private lateinit var sp: SharedPreferences
    private var words = arrayOf(
        "SERVICE",
        "COMPANY",
        "LAPTOP",
        "EXPRESS",
        "DESCRIPTION",
        "FORWARD",
        "CONFIDENT",
        "PLANET",
        "BOUNCE",
        "LIMITED",
        "PRESSURE",
        "BOOKLET",
        "VEGETABLES",
        "MANAGEMENT",
        "INVITATION",
        "CALENDAR",
        "TEACHER",
        "MIXTURE",
        "VOLLEYBALL",
        "CRICKET",
        "GRAVITY",
        "DOMINATION",
        "WALK",
        "CELEBRITIES",
        "SOLDIERS",
        "SLEEPOVER",
        "DINNER",
        "ORANGE",
        "GRATEFUL",
        "CONFIDENT",
        "HOURGLASS",
        "UNIVERSE",
        "GALAXY",
        "PARENTS",
        "POSTMAN",
        "INSTRUMENT",
        "CHOCLATE",
        "VILLAGE",
        "POWERHOUSE",
        "SPOT",
        "AWESOME",
        "INTERNET",
        "PROMISE",
        "MOBILE",
        "CELLPHONE",
        "YOUTUBE",
        "FACEBOOK",
        "PRICE",
        "INTEREST",
        "GRUMPY",
        "CHAMPION",
        "TITLE",
        "LEAGUE",
        "SEARCH",
        "CONDITION",
        "SITUATION",
        "OCTOBER",
        "MARCH",
        "NEPOTISM",
        "REFERENCE",
        "TECHNICAL",
        "STUDENT",
        "LATENCY",
        "ARGUMENT",
        "CHILDREN",
        "TERRIBLE",
        "BRANCH",
        "SUMMON",
        "SELDOM",
        "TYPEWRITER",
        "KEYBOARD",
        "SWITCH",
        "CONSISTENT",
        "KNOWLEDGE",
        "IMPORTANT",
        "BENEFIT",
        "CLEAR",
        "AVOID",
        "COMPLETE",
        "REPORT",
        "CODING",
        "EFFECT",
        "DEFINITION",
        "DEPEND",
        "SPEAKER",
        "EARPHONE",
        "TELEVISION",
        "RADIO",
        "SWIMMING",
        "DANCING",
        "BOOKSHELF",
        "DOOR",
        "WINDOW",
        "ENTRANCE",
        "PASTRY",
        "FOOD",
        "NUTRIENT",
        "VITAMIN",
        "SPELLING",
        "CULTURE",
        "TWITTER",
        "CONVINCE",
        "HAPPY",
        "SELECTION",
        "COMBINATION",
        "BALANCE",
        "INDIA",
        "AUSTRALIA",
        "TURKEY",
        "TITANIC",
        "FITNESS",
        "GYM",
        "SHIFT",
        "COLOSSAL",
        "HUMANITY",
        "BALLOON",
        "APPLY",
        "BELIEF",
        "PURCHASE",
        "RECEIVE",
        "SUPREME",
        "WOMEN",
        "CUSHION",
        "TABLE",
        "CHARGER",
        "TRANSPORT",
        "FRIDGE",
        "LAUGH",
        "SOLO",
        "PLAN",
        "FROST",
        "STORAGE",
        "DISK",
        "SPEED",
        "STYLISH",
        "AMAZING",
        "GREAT",
        "TIMETABLE",
        "FORMATION",
        "APRIL",
        "EIGHTEEN",
        "DOWN",
        "KITCHEN",
        "GRANDMOTHER",
        "BLOOD",
        "NOTHING",
        "SOUND",
        "DONUT",
        "SANDWICH",
        "SPLIT",
        "ROCK",
        "SIMPLE",
        "TOUGH",
        "ABS",
        "CONVEX",
        "BUTTER",
        "YOGURT",
        "STAMP",
        "GUESS",
        "STRAW",
        "CLIMB",
        "MONKEY",
        "THEFT",
        "NAIVE",
        "EFFICIENT",
        "PREPARE",
        "GIGGLE",
        "COMMENTS",
        "STONE",
        "PAPER",
        "SCISSOR",
        "SECURITY",
        "COBRA",
        "INTERVIEW",
        "STEEP",
        "CREEP",
        "CUTE",
        "EXILE",
        "ROBUST",
        "PLATFORM",
        "TRAIN",
        "BRAIN",
        "SKULL",
        "SKILL",
        "BOUNCE",
        "COUNTRY",
        "STATE",
        "WEIGHT",
        "MASS",
        "PHYSICS",
        "STOVE",
        "BUFFALO",
        "CLIP",
        "CHIPS",
        "BRIM",
        "COCO",
        "MOROCCO",
        "STEADY",
        "JUICE",
        "CLASH",
        "GUARDIAN",
        "EMPLOYEE",
        "CUSTOMER",
        "AUTHORITY",
        "FEEBLE",
        "LIGHT",
        "PRANK",
        "INFLATION",
        "GOOD",
        "AUNT",
        "UNCLE",
        "THANK",
        "BLANK",
        "STUPID",
        "CIRCLE",
        "PENTAGON",
        "COOKING",
        "BORING",
        "NOISE",
        "CALM",
        "SILENT",
        "RIPPLE",
        "WEATHER",
        "FANTASTIC"
    )
    private var noOfWords: Int = 0
    private lateinit var answerStatus: TextView
    private lateinit var databse: SQLiteDatabase
    private lateinit var currentWord: String
    private lateinit var answerArray: CharArray
    private lateinit var solveArray: CharArray
    private lateinit var wordTextView: TextView
    private lateinit var chanceImageViews: Array<ImageView>
    private var chances = 8;
    private var rejectedString = "Rejected letters: "
    private lateinit var rejectedTextView: TextView
    private var rightLetterArray = arrayOf("Good!", "Correct!", "Yes!", "Great!")
    private var wrongLetterArray = arrayOf("Oops!", "Oh No!", "Guess again!", "Keep Trying!")

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

    private fun insertInDatabase(word: String) {
        databse.execSQL("INSERT INTO words(word) VALUES ('$word')")
    }

    fun insertLetter(view: View) {
        val typedChar = view.tag.toString()[0]
        if (currentWord.contains(view.tag.toString())) {
            setAnswerStatusText(true)
            for (i in answerArray.indices) {
                if (typedChar == answerArray[i]) {
                    solveArray[i] = typedChar
                }
            }

            changeTextView()

            if (!solveArray.contains('_')) {
                resetRejectedTextView()
                Toast.makeText(applicationContext, "Well Done", Toast.LENGTH_SHORT).show()
                chances = 8
                setImageViews()
                val handler = Handler()
                handler.postDelayed({
                    selectAndOperateMode()
                }, 1500)
            }
        } else {
            setAnswerStatusText(false)
            updateRejectedTextView(typedChar)
            chances--
            chanceImageViews[chances].setImageResource(R.drawable.ic_baseline_favorite_border_24)
            if (chances == 0) {
                resetRejectedTextView()
                chances = 8
                Toast.makeText(applicationContext, "Chances over", Toast.LENGTH_SHORT).show()
                showWordAlertDialog()
            }
        }
    }

    private fun setAnswerStatusText(correct: Boolean) {
        val random: Random = Random()
        val index = random.nextInt(4)
        if (correct) {
            answerStatus.text = rightLetterArray[index]
        } else {
            answerStatus.text = wrongLetterArray[index]
        }
    }

    private fun updateRejectedTextView(typedChar: Char) {
        rejectedString += "$typedChar "
        rejectedTextView.text = rejectedString
    }

    private fun resetRejectedTextView() {
        rejectedString = "Rejected Letters: "
        rejectedTextView.text = rejectedString
    }

    private fun setImageViews() {
        for (i in 0 until chances) {
            chanceImageViews[i].setImageResource(R.drawable.ic_baseline_favorite_24)
        }
    }

    private fun showWordAlertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this).inflate(R.layout.your_score, null, false)
        val wordTextView: TextView = view.findViewById(R.id.show_usual_score)
        wordTextView.text = "Word was: $currentWord"
        val ok_btn = view.findViewById<Button>(R.id.ok_button_score)
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        ok_btn.setOnClickListener {
            dialog.dismiss()
            selectAndOperateMode()
        }

        dialog.show()
    }

    private fun selectAndOperateMode() {
        if (aloneMode) {
            operateOnAloneMode()
        } else
            operateOnFriendMode()
    }

    private fun createCurrentWord(): String {
        val random: Random = Random()
        val wordIndex = random.nextInt(noOfWords) + 1 //1 to noOfWords
        val c: Cursor = databse.rawQuery("SELECT word FROM words WHERE id = $wordIndex", null)
        val wordColIndex = c.getColumnIndex("word")
        var word: String = "EMPTY"
        if(c.moveToFirst()) {
            while (!c.isAfterLast) {
                word = c.getString(wordColIndex)
                c.moveToNext()
            }
            c.close()
            return word.toUpperCase(Locale.ROOT)
        }
        c.close()
        return word.toUpperCase(Locale.ROOT)
    }

    private fun operateOnAloneMode() {
        answerStatus.text = ""
        currentWord = createCurrentWord()
        createAnsArray()
        createSolveArray()
        changeTextView()
        setImageViews()
    }

    private fun createAnsArray() {
        answerArray = currentWord.toCharArray()
    }

    private fun createSolveArray() {
        solveArray = CharArray(answerArray.size)
        for (i in answerArray.indices) {
            if (answerArray[i] == 'A' || answerArray[i] == 'E' || answerArray[i] == 'I' || answerArray[i] == 'O' || answerArray[i] == 'U') {
                solveArray[i] = answerArray[i]
            } else
                solveArray[i] = '_'
        }
    }

    private fun changeTextView() {
        var s = "";
        for (i in solveArray.indices) {
            s += solveArray[i]
        }

        wordTextView.text = s
    }

    private fun initImageViews() {
        chanceImageViews = arrayOf(
            findViewById(R.id.chance_1),
            findViewById(R.id.chance_2),
            findViewById(R.id.chance_3),
            findViewById(R.id.chance_4),
            findViewById(R.id.chance_5),
            findViewById(R.id.chance_6),
            findViewById(R.id.chance_7),
            findViewById(R.id.chance_8),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whats_the_word_game)

        aloneMode = intent.getBooleanExtra("alone", true)
        wordTextView = findViewById(R.id.word_to_be_guessed)
        rejectedTextView = findViewById(R.id.rejected_letters)
        answerStatus = findViewById(R.id.answer_status)
        rejectedTextView.text = rejectedString
        initImageViews()

        databse = openOrCreateDatabase("games", MODE_PRIVATE, null)
        databse.execSQL("CREATE TABLE IF NOT EXISTS words(id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR)")

        sp = this.getSharedPreferences("com.atandroidlabs.braingames20", MODE_PRIVATE)
        noOfWords = sp.getInt("noOfWords",  0)

        currentWord = ""

        if (noOfWords == 0) {
            for (word in words) {
                insertInDatabase(word)
            }
            sp.edit().putInt("noOfWords", words.size).apply()
            noOfWords = words.size
        }

        words = emptyArray()

        selectAndOperateMode()
    }

    private fun operateOnFriendMode() {
        answerStatus.text = ""
        showEnterWordDialog()
    }

    private fun showEnterWordDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val view: View = LayoutInflater.from(this).inflate(R.layout.enter_word_layout, null, false)
        val editText: EditText = view.findViewById(R.id.word_editText)
        val doneBtn: Button = view.findViewById(R.id.done_writing)
        builder.setView(view)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        doneBtn.setOnClickListener {
            val string = editText.text.toString().trim()
            if (string.contentEquals("")) {
                currentWord = "EMPTY"
                Toast.makeText(applicationContext, "Please Enter word", Toast.LENGTH_SHORT).show()
            } else {
                currentWord = string.toUpperCase(Locale.ROOT)
                dialog.dismiss()
                createAnsArray()
                createSolveArray()
                changeTextView()
                setImageViews()
            }
        }
        dialog.show()
    }
}