package com.atandroidlabs.braingames20

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.atandroidlabs.braingames20.MatchThePair.MatchThePairActivity
import com.atandroidlabs.braingames20.MentalMaths.MentalMathsMainActivity
import com.atandroidlabs.braingames20.Sequence.SequenceMainActivity
import com.atandroidlabs.braingames20.TicTacToe.TicTacToeActivity
import com.atandroidlabs.braingames20.WhatsTheWord.WhatsTheWordMainActivity

class GameAdapter(var context: Context, var games: ArrayList<Game>) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.textView.text = games[position].name
        holder.imageView.setImageResource(games[position].img_id)
        holder.itemView.setOnClickListener(View.OnClickListener {
            var intent: Intent
            when (position) {
                0 -> {
                    //sequence
                    intent = Intent(context, SequenceMainActivity::class.java)
                }
                1 -> {
                    //match the pair
                    intent = Intent(context, MatchThePairActivity::class.java)
                }
                2 -> {
                    //mental maths
                    intent = Intent(context, MentalMathsMainActivity::class.java)
                }
//                3 -> {
//                    //guess the celeb
//                    intent = Intent(context, GuessTheCelebMainActivity::class.java)
//                }
                3 -> {
                    //what's the word
                    intent = Intent(context, WhatsTheWordMainActivity::class.java)
                }
                4 -> {
                    //tic tac toe
                    intent = Intent(context, TicTacToeActivity::class.java)
                }
                else -> {
                    //unknown situation
                    intent = Intent(context, TicTacToeActivity::class.java)
                }
            }
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            context.startActivity(intent)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_background, parent, false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.findViewById<TextView>(R.id.game_name)
        var imageView = itemView.findViewById<ImageView>(R.id.game_image)
    }
}