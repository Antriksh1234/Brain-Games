package com.atandroidlabs.braingames20.GuessTheCeleb

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.atandroidlabs.braingames20.R
import java.util.*

class GuessTheCelebGameActivity : AppCompatActivity() {

    private var photo: ImageView? = null
    private var score: TextView? = null
    private var description: TextView? = null
    private lateinit var buttons: Array<Button>
    private var no_of_questions = 0
    private var random: Random? = null
    private var high_score = 0
    private var celebs_done: ArrayList<Int>? = null
    private var no_of_correct = 0
    private var correct_option = 0
    private var celebrities = arrayOfNulls<Celebrity>(60)

    private lateinit var celebDescription: Array<String>
    private lateinit var celeb_name: Array<String>
    private lateinit var photo_id: IntArray

    private fun setCelebDescription() {
        celebDescription = arrayOf(
            "Also known as the perfectionist,he is no doubt one of the most dedicated actors in this world. With his recent success in India and China markets combined he has got the title of World's Biggest Superstar. He is known for his brilliant roles as 'Bhuvan','Rancho','PK' ",
            " He is well known for his Khiladi series, an Indian version of James Bond, such as Mr. Bond (1992), Khiladi (1992), Main Khiladi Tu Anari (1994), Mr. & Mrs. Khiladi (1997) and Khiladi 420 (2000). He has some great films as Mission Mangal, Toilet ek prem katha ",
            " He made his film debut with Phool Aur Kaante in 1991 and received a Filmfare Award for Best Male Debut for his performance in the film. Throughout his career he has given many critically and commercially successful films including Raincoat (2004), Yuva (2004), Omkara (2006), Golmaal: Fun Unlimited (2006), Golmaal Returns (2008)",
            "was born on November 12, 1940 in Hyderabad, Hyderabad State, British India. He was an actor and director, known for Sholay (1975), Yaarana (1981) and Maa Kasam (1985). He was married to Shaila Khan. He died on July 27, 1992 in Bombay, Maharashtra, India.",
            "Also called as the Angry Young Man and the Bollywood's Shahenshah,This man has no stopping,known for brilliant hits like Sholay, Chote Miyan Bade Miyan,Baghban and many more",
            "He is known for his good sense of humour. Has been a part of a ton of a movies like the Golmaal series and Jolly LLb",
            "He is the middle son of Bollywood actor and politician Vinod Khanna, and the younger brother of actor Rahul Khanna.",
            "He started off his career as a popular radio jockey, and eventually became a VJ on MTV India and one of the most popular hosts in India. He shot to stardom with the runaway hit, 'Vicky Donor' in 2012. ",
            "His career has spanned nearly 30 years including a distinctive presence in Bollywood cinema, television serials, and reality game shows. He is best remembered for hosting the singing show Antakshari, produced by ZEE TV.",
            "Also known as the 'Lakhkhan',he is no doubt one of the great actors of bollywood. He has an elder brother, Boney, a sister, Reena, and a younger brother, Sanjay.",
            "He is an actor, known for 3 Idiots (2009), Munna Bhai M.B.B.S. (2003) and Don 2 (2011). He has been married to Zenobia since January 28, 1985.",
            "Well known globally for his versatile roles, unmatchable dancing skills and attractive looks.Known for movies such as the Krish , Kaho Na Pyaar Hai",
            "He was born on April 23, 1969 in Belwa, Bihar, India. He is known for his work on Gangs of Wasseypur (2012), Satya (1998) and Aks (2001).",
            "'Kabhi Kabhi apun ko lgta hai ki apunich bhagwaan hai' Well this is enough to describe about this man. Also called as the nawaaz of the bollywood",
            " He is a producer and actor, known for Hera Pheri (2000), OMG: Oh My God! (2012) and Hungama (2003).",
            "He was born on 2 November 1965 in New Delhi, India. He married Gauri Khan on 25 October 1991. They have three children, son Aryan Khan (b. 1997), son AbRam (b.2013) and daughter Suhana (b. 2000).",
            " He is an actor, known for 3 Idiots (2009), Rang De Basanti (2006) and Golmaal: Fun Unlimited (2006). He has been married to Prerna Chopra since 2000.",
            " born on April 24, 1987 in Mumbai, Maharashtra, India. He is an actor and assistant director, known for Badlapur(2015),Humpty Sharma Ki Dulhania and many more",
            "A right-handed top-order batsman plays for India, He is regarded as one of the best batsmen in the world. He plays for Royal Challengers Bangalore in the Indian Premier League.",
            " He was born on July 23, 1989 in Fulham, London, England. He got worldwide popularity when he was casted into a Chris Columbus film as Harry Potter. ",
            "He is also nicknamed as the Fresh Prince and Mr July.He is an American actor, comedian, producer, rapper, and songwriter. His some films include MIB 3, Pursuit of Happiness, Bad Boys. ",
            "Also known as The Rock,he was born on May 2, 1972 in Hayward, California. He also appeared in 'The Mummy Returns' (2001)",
            " He was born Mark Sinclair in Alameda County, California, along with his fraternal twin brother, Paul Vincent. His movies include Guardians of the galaxy, XXX The return of the xander cage and Bloodshot",
            "Popularly known for the 'Mr Bean' TV series in India ,but apart from that he also appeared in several movies, including Dead on Time (1983), Pleasure at Her Majesty's (1976) (aka 'Monty Python Meets Beyond the Fringe'), Never Say Never Again (1983), and The Tall Guy (1989).",
            "Here you go marvel fans!!''Iron Man' The name is enough!!",
            "Hong Kong's cheeky, lovable and best known film star, he endured many years of long, hard work and multiple injuries to establish international success after his start in Hong Kong's manic martial arts cinema industry.",
            "He is a Portuguese professional footballer who plays as a forward for Serie A club Juventus and captains the Portugal national team. Often considered the best player in the world and widely regarded as one of the greatest players of all time",
            "This Jamaican sprinter is a world record holder in the 100 metres, 200 metres and 4 × 100 metres relay. Owing to his achievements and dominance in sprint competition, he is widely considered to be the greatest sprinter of all time.",
            "He is the founder, CEO and chief engineer/designer of SpaceX, co-founder, CEO and product architect of Tesla, Inc. founder of The Boring Company, co-founder of Neuralink; and co-founder and initial co-chairman of OpenAI.",
            "This guy had a diverse and brilliant career. His movies include well known Titanic,Romeo + Juliet,Critters 3",
            "She is well renowned for her role as 'Devsena' in Bahubali Franchise,She is a South Indian actress with many movies like Arundhati and Vikramakudu which made her a renowned Telugu actress",
            " She is an actress, known for Kuch Kuch Hota Hai (1998), My Name Is Khan (2010) and Fanaa (2006). She has been married to Ajay Devgn since February 24, 1999. ",
            "She is one of the top actress in Bollywood. She is a daughter of Prakash Padukone ,a famous badminton player. Her movies include Yeh Jawaani Hai Deewani, Chennai Express and Piku",
            "Well known for her dancing and expressions, Also called as the dhadhak dhadhak girl,words fall short for her introduction. Her movies include Tezaab, Devdas, Hum Aapke hai kon",
            "She is the winner of Miss World (2000). Her well known movies include Barfi, Krish, Don,Bajirao Mastani. ",
            "She made her debut with a hit 'Rab Ne bana Di Jodi' in SRK starrer film. Her other movies include 'PK','Dil Dhadakne Do','Jab Harry Met Sejal' ",
            "For her portrayal of the septuagenarian sharpshooter Chandro Tomar in Saand Ki Aankh (2019), she won the Filmfare Critics Award for Best Actress. Her other brilliant performances include Toilet: Ek Prem Katha (2017), Shubh Mangal Saavdhan (2017), Bala (2019), and Pati Patni Aur Woh (2019).",
            "She started her television career as a child artist in 2010 with Disney India's The Suite Life of Karan & Kabir (2012). Her movies include Marjaavaan and Student of the year 2.",
            "She primarily works in Telugu Tamil and Hindi movies. Her successful movies include  Pink (2016) and Badla (2019), the war drama The Ghazi Attack (2017), the action comedy Judwaa 2 (2017) and the space drama Mission Mangal (2019). ",
            "She made her debut with Student of the year 2 along with Tara Sutaria. She has also worked in Pati Patni or woh (2019)",
            "She established herself by starring in several commercially successful romantic films, including Chalte Chalte (2003), Hum Tum (2004), Veer-Zaara (2004), and Kabhi Alvida Naa Kehna (2006), and the crime comedy Bunty Aur Babli (2005).",
            "She received the Filmfare Award for Best Actress in 2003 for her performance in the drama Kal Ho Naa Ho. She went on to play leading roles in two consecutive annual top-grossing films in India, the science fiction film Koi... Mil Gaya (2003) and the romantic drama Veer-Zaara (2004).",
            "She is the daughter of actors Amrita Singh and Saif Ali Khan and the paternal granddaughter of Mansoor Ali Khan Pataudi and Sharmila Tagore.  After graduating from Columbia University, she ventured into acting by playing the leading lady in the 2018 films Kedarnath and Simmba.",
            "She is the daughter of filmmaker Mahesh Bhatt and actress Soni Razdan. After making her acting debut as a child in the 1999 thriller Sangharsh, she played her first leading role in Karan Johar's teen drama Student of the Year (2012). ",
            "She (born 12 March 1984) is an Indian playback singer, composer and music producer. She has received four National Film Awards, four Kerala State Film Awards, two Tamil Nadu State Film Awards, seven Filmfare Awards including six for Best Female Playback Singer and ten Filmfare Awards South. She has recorded songs for film music and albums in various Indian languages and has established herself as a leading playback singer of Indian cinema.",
            "She (born 6 June 1988) is a playback singer. She began performing at religious events at the age of four and participated in the second season of the singing reality show, Indian Idol, in which she was eliminated early. ",
            "She is the greatest female playback singer in Indian history. She has won several national awards and also honoured with the 'Bharat Ratna'",
            "She (born 21 September 1980) is an Indian actress who appears in Hindi films. She is the daughter of actors Randhir Kapoor and Babita, and the younger sister of actress Karisma Kapoor. ",
            "She had her first commercial success with a brief role in the 2016 sports biopic M.S. Dhoni: The Untold Story. Her career advanced with starring roles in the Telugu political drama Bharat Ane Nenu (2018) as well as the Hindi romantic drama Kabir Singh and the comedy Good Newwz (both 2019).",
            "She gained recognition following the release of her first two studio albums, Music of the Sun (2005) and A Girl like Me (2006), both of which were influenced by Caribbean music and peaked within the top ten of the US Billboard 200 chart.",
            "She (born December 13, 1989) is an American singer-songwriter. Her best of the works includes 'fearless','Red','Reputation'.",
            "She is an American singer, songwriter, and television judge. Her albums include 'Teenage Dream', 'One of the Boys', 'Prism'. ",
            "She made her film debut in Superbad (2007), and received positive media attention for her role in Zombieland (2009). The 2010 teen comedy 'Easy A' was her first starring role, earning her nominations for the BAFTA Rising Star Award and a Golden Globe Award for Best Actress. This breakthrough was followed with further success in the romantic comedy Crazy, Stupid, Love (2011) and the drama The Help (2011).",
            "She starred as 'Belle' in the musical romantic fantasy 'Beauty and the Beast' (2017) and Meg March in the coming-of-age film Little Women (2019), the latter of which was nominated for the Academy Award for Best Picture. Her other film roles include Regression (2015), Colonia (2015), and The Circle (2017).",
            "She is an Academy Award-winning actress who rose to fame after her role in Girl, Interrupted (1999), playing the title role in the \"Lara Croft\" blockbuster movies, as well as Mr. & Mrs. Smith (2005), Wanted (2008), Salt (2010) and Maleficent (2014). ",
            "She began playing the role of Black Widow in the Marvel Cinematic Universe with Iron Man 2. She went on to star in the science fiction films Her (2013), Under the Skin (2013), Lucy (2014), and Ghost in the Shell (2017).",
            "Her first major role came as a main cast member on the sitcom The Bill Engvall Show (2007–2009). She made her film debut in a supporting role in the drama Garden Party (2008), and had her breakthrough playing a poverty-stricken teenager in the independent drama Winter's Bone (2010). Her career progressed with her starring roles as the mutant Mystique in the X-Men film series (2011–2019) and Katniss Everdeen in the Hunger Games film series (2012–2015). ",
            "She began her acting career at age 12 by starring as the young protégée of a hitman in the action drama film Léon: The Professional (1994). While in high school, she made her Broadway theatre debut in a 1998 production of The Diary of a Young Girl and gained international recognition for starring as Padmé Amidala in Star Wars: Episode I – The Phantom Menace (1999).",
            "She (born September 26, 1981) is an American professional tennis player and former world No. 1 in women's single tennis. She has won 23 Grand Slam singles titles, the most by any player in the Open Era, and the second-most of all time behind Margaret Court (24). ",
            "She is an American politician, diplomat, lawyer, writer, and public speaker. She served as the 67th United States Secretary of State from 2009 to 2013, as United States senator from New York from 2001 to 2009, and as First Lady of the United States from 1993 to 2001.",
        )
    }

    private fun setCeleb_name() {
        celeb_name = arrayOf(
            "Aamir Khan",
            "Akshay Kumar",
            "Ajay Devgan",
            "Amjad Khan",
            "Amitabh Bachchan",
            "Arshad Warsi",
            "Akshaya Khanna",
            "Ayushmann Khurana",
            "Annu Kapoor",
            "Anil Kapoor",
            "Boman Irani",
            "Hritik Roshan",
            "Manoj Bajpayee",
            "Nawazuddin Siddiqui",
            "Paresh Rawal",
            "Shahrukh Khan",
            "Sharman Joshi",
            "Varun Dhawan",
            "Virat Kohli",
            "Daniel Radcliffe",
            "Will Smith",
            "Dwayne Johnson",
            "Vin Diesel",
            "Rowan Atkinson",
            "Robert Downey Jr.",
            "Jackie Chan",
            "Christiano Ronaldo",
            "Usain Bolt",
            "Elon Musk",
            "Leonardo DiCaprio",
            "Anushka Shetty",
            "Kajol",
            "Deepika Padukone",
            "Madhuri Dixit",
            "Priyanka Chopra",
            "Anushka Sharma",
            "Bhumi Pednekar",
            "Tara Sutaria",
            "Taapsee Pannu",
            "Ananya Pandey",
            "Rani Mukerji",
            "Preity Zinta",
            "Sara Ali Khan",
            "Alia Bhatt",
            "Shreya Ghoshal",
            "Neha Kakkar",
            "Lata Mangeshkar",
            "Kareena kapoor",
            "Kiara Advani",
            "Rihanna",
            "Taylor Swift",
            "Katy Perry",
            "Emma Stone",
            "Emma Watson",
            "Angelina Jolie",
            "Scarlett Johansson",
            "Jennifer Lawrence",
            "Natalie Portman",
            "Serena Williams",
            "Hillary Clinton"
        )
    }

    private fun setPhoto_id() {
        //Male celebs
        photo_id[0] = R.drawable.celeb0
        photo_id[1] = R.drawable.celeb1
        photo_id[2] = R.drawable.celeb2
        photo_id[3] = R.drawable.celeb3
        photo_id[4] = R.drawable.celeb4
        photo_id[5] = R.drawable.celeb5
        photo_id[6] = R.drawable.celeb6
        photo_id[7] = R.drawable.celeb7
        photo_id[8] = R.drawable.celeb8
        photo_id[9] = R.drawable.celeb9
        photo_id[10] = R.drawable.celeb10
        photo_id[11] = R.drawable.celeb11
        photo_id[12] = R.drawable.celeb12
        photo_id[13] = R.drawable.celeb13
        photo_id[14] = R.drawable.celeb14
        photo_id[15] = R.drawable.celeb15
        photo_id[16] = R.drawable.celeb16
        photo_id[17] = R.drawable.celeb17
        photo_id[18] = R.drawable.celeb18
        photo_id[19] = R.drawable.celeb19
        photo_id[20] = R.drawable.celeb20
        photo_id[21] = R.drawable.celeb21
        photo_id[22] = R.drawable.celeb22
        photo_id[23] = R.drawable.celeb23
        photo_id[24] = R.drawable.celeb24
        photo_id[25] = R.drawable.celeb25
        photo_id[26] = R.drawable.celeb26
        photo_id[27] = R.drawable.celeb27
        photo_id[28] = R.drawable.celeb28
        photo_id[29] = R.drawable.celeb29

        //Female Celebs
        photo_id[30] = R.drawable.celeb30
        photo_id[31] = R.drawable.celeb31
        photo_id[32] = R.drawable.celeb32
        photo_id[33] = R.drawable.celeb33
        photo_id[34] = R.drawable.celeb34
        photo_id[35] = R.drawable.celeb35
        photo_id[36] = R.drawable.celeb36
        photo_id[37] = R.drawable.celeb37
        photo_id[38] = R.drawable.celeb38
        photo_id[39] = R.drawable.celeb39
        photo_id[40] = R.drawable.celeb40
        photo_id[41] = R.drawable.celeb41
        photo_id[42] = R.drawable.celeb42
        photo_id[43] = R.drawable.celeb43
        photo_id[44] = R.drawable.celeb44
        photo_id[45] = R.drawable.celeb45
        photo_id[46] = R.drawable.celeb46
        photo_id[47] = R.drawable.celeb47
        photo_id[48] = R.drawable.celeb48
        photo_id[49] = R.drawable.celeb49
        photo_id[50] = R.drawable.celeb50
        photo_id[51] = R.drawable.celeb51
        photo_id[52] = R.drawable.celeb52
        photo_id[53] = R.drawable.celeb53
        photo_id[54] = R.drawable.celeb54
        photo_id[55] = R.drawable.celeb55
        photo_id[56] = R.drawable.celeb56
        photo_id[57] = R.drawable.celeb57
        photo_id[58] = R.drawable.celeb58
        photo_id[59] = R.drawable.celeb59
    }

    //Sets the array of celebs
    private fun setCelebrities() {
        for (i in 0..59) {
            celebrities[i] = Celebrity(celeb_name[i], celebDescription[i], photo_id[i], i)
        }
    }

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

    private var celeb_number = 0

    fun setUpGame() {
        photo!!.setBackgroundResource(R.drawable.questionmark)
        celeb_number = random!!.nextInt(60)
        while (celebs_done!!.contains(celeb_number)) {
            celeb_number = random!!.nextInt(60)
        }
        celebs_done!!.add(celeb_number)
        description!!.text = celebrities[celeb_number]!!.getDescription()
        correct_option = random!!.nextInt(4)
        val other1: Int
        val other2: Int
        val other3: Int
        if (celeb_number < 19) {
            if (celeb_number >= 14) {
                other1 = celeb_number - 3
                other2 = celeb_number - random!!.nextInt(2) - 1
                other3 = celeb_number - random!!.nextInt(5) - 4
            } else if (celeb_number <= 5) {
                other1 = celeb_number + 4
                other2 = celeb_number + random!!.nextInt(3) + 1
                other3 = celeb_number + random!!.nextInt(5) + 6
            } else {
                other1 = celeb_number - 4
                other2 = celeb_number + random!!.nextInt(3) + 2
                other3 = celeb_number + 5
            }
        } else if (celeb_number < 30) {
            if (celeb_number >= 26) {
                other1 = celeb_number - 4
                other2 = celeb_number - random!!.nextInt(3) - 1
                other3 = celeb_number - random!!.nextInt(2) - 5
            } else if (celeb_number <= 22) {
                other1 = celeb_number + 7
                other2 = celeb_number + random!!.nextInt(2) + 1
                other3 = celeb_number + random!!.nextInt(3) + 4
            } else {
                other1 = celeb_number + random!!.nextInt(2) + 1
                other2 = celeb_number + 3
                other3 = celeb_number + random!!.nextInt(3) + 4
            }
        } else if (celeb_number < 49) {
            if (celeb_number >= 46) {
                other1 = celeb_number - random!!.nextInt(4)
                other2 = celeb_number - random!!.nextInt(3) - 7
                other3 = celeb_number - random!!.nextInt(2) - 5
            } else if (celeb_number <= 32) {
                other1 = celeb_number + random!!.nextInt(4) + 1
                other2 = celeb_number + random!!.nextInt(2) + 5
                other3 = celeb_number + random!!.nextInt(2) + 11
            } else {
                other1 = celeb_number + random!!.nextInt(2) + 1
                other2 = celeb_number + random!!.nextInt(3) + 3
                other3 = celeb_number - random!!.nextInt(2) - 1
            }
        } else {
            if (celeb_number >= 56) {
                other1 = celeb_number - random!!.nextInt(2) - 1
                other2 = celeb_number - random!!.nextInt(2) - 3
                other3 = celeb_number - random!!.nextInt(2) - 5
            } else if (celeb_number <= 51) {
                other1 = celeb_number + random!!.nextInt(2) + 1
                other2 = celeb_number + random!!.nextInt(2) + 3
                other3 = celeb_number + random!!.nextInt(2) + 5
            } else {
                other1 = celeb_number + random!!.nextInt(2) + 1
                other2 = celeb_number + random!!.nextInt(2) + 3
                other3 = celeb_number - random!!.nextInt(2) - 1
            }
        }
        buttons[correct_option].text = celebrities[celeb_number]!!.getName()
        if (correct_option == 0) {
            buttons[1].text = celebrities[other1]!!.getName()
            buttons[2].text = celebrities[other2]!!.getName()
            buttons[3].text = celebrities[other3]!!.getName()
        } else if (correct_option == 1) {
            buttons[0].text = celebrities[other1]!!.getName()
            buttons[2].text = celebrities[other2]!!.getName()
            buttons[3].text = celebrities[other3]!!.getName()
        } else if (correct_option == 2) {
            buttons[0].text = celebrities[other1]!!.getName()
            buttons[1].text = celebrities[other2]!!.getName()
            buttons[3].text = celebrities[other3]!!.getName()
        } else {
            buttons[0].text = celebrities[other1]!!.getName()
            buttons[1].text = celebrities[other2]!!.getName()
            buttons[2].text = celebrities[other3]!!.getName()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guess_the_celeb_game)

        score = findViewById(R.id.score_guess)
        photo = findViewById(R.id.celeb_photo)
        description = findViewById(R.id.celeb_description)

        buttons = arrayOf(
            findViewById(R.id.celeb_option1),
            findViewById(R.id.celeb_option2),
            findViewById(R.id.celeb_option3),
            findViewById(R.id.celeb_option4)
        )

        no_of_questions = 0.also { no_of_correct = it }

        random = Random()

        photo_id = IntArray(60)
        setCeleb_name()
        setCelebDescription()
        setPhoto_id()
        setCelebrities()

        celebs_done = ArrayList()

        //Start game
        setUpGame()
    }

    fun checkCelebAnswer(view: View) {
        try {
            val tag = view.tag.toString()
            if (tag.contentEquals((correct_option + 1).toString())) {
                //right answer increment score
                no_of_correct++
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            }
            no_of_questions++
            score!!.text = "Score\n$no_of_correct/$no_of_questions"
            photo!!.animate().rotationYBy(360f).duration = 500
            photo!!.setBackgroundResource(celebrities[celeb_number]!!.getPhoto_id())
            val timer: CountDownTimer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    for (i in 0..3) {
                        buttons[i].isEnabled = false
                        buttons[i].alpha = 0.3f
                    }
                }

                override fun onFinish() {
                    for (i in 0..3) {
                        buttons[i].isEnabled = true
                        buttons[i].alpha = 1f
                    }
                    if (no_of_questions == 15) {
                        val database: SQLiteDatabase = this@GuessTheCelebGameActivity.openOrCreateDatabase(
                            "games",
                            MODE_PRIVATE,
                            null
                        )
                        val c = database.rawQuery(
                            "SELECT * FROM celeb",
                            null
                        )
                        val scoreIndex = c.getColumnIndex("score")
                        high_score = if (c.moveToFirst()) {
                            c.getInt(scoreIndex)
                        } else {
                            0
                        }
                        if (no_of_correct >= high_score) {
                            val builder = AlertDialog.Builder(this@GuessTheCelebGameActivity)
                            val alert_dialog_view =
                                layoutInflater.inflate(R.layout.score_showing_dialog, null, false)
                            builder.setView(alert_dialog_view)
                            val dialog = builder.create()
                            val save_high_score_button =
                                alert_dialog_view.findViewById<Button>(R.id.save_high_score)
                            val nameText =
                                alert_dialog_view.findViewById<EditText>(R.id.high_scorer)
                            val score_show =
                                alert_dialog_view.findViewById<TextView>(R.id.score_show_alert_dialog)
                            score_show.text = "Score: $no_of_correct"
                            save_high_score_button.setOnClickListener {
                                val name = nameText.text.toString().trim()
                                if (isNotValidName(name)) {
                                    askToEnterName()
                                } else {
                                    if (no_of_correct > high_score) {
                                        database.execSQL("DELETE FROM celeb WHERE score = $high_score")
                                    }
                                    database.execSQL("INSERT INTO celeb (name, score) VALUES('$name',$no_of_correct)")
                                }
                                Toast.makeText(
                                    this@GuessTheCelebGameActivity,
                                    "High Score added",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                                finish()
                            }
                            dialog.show()
                        } else {
                            showScoreAlertDialog()
                            c.close()
                        }
                    } else {
                        setUpGame()
                    }
                }
            }
            timer.start()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showScoreAlertDialog() {
        val builder = AlertDialog.Builder(this@GuessTheCelebGameActivity)
        val alert_dialog_view: View =
            layoutInflater.inflate(R.layout.your_score, null, false)
        val ok_button =
            alert_dialog_view.findViewById<Button>(R.id.ok_button_score)
        val score_show =
            alert_dialog_view.findViewById<TextView>(R.id.show_usual_score)
        score_show.text = "Your score: $no_of_correct"
        builder.setView(alert_dialog_view)
        val dialog = builder.create()
        ok_button.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        dialog.show()
    }

    private fun askToEnterName() {
        Toast.makeText(
            this@GuessTheCelebGameActivity,
            "Enter a name!",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun isNotValidName(name: String): Boolean {
        return name.contentEquals("")
    }
}