package com.atandroidlabs.braingames20

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HighScoreAdapter(var context: Context, var scoreList: ArrayList<Player>) : RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>() {

    override fun getItemCount(): Int {
        return scoreList.size
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        holder.userName.text = scoreList[position].name
        holder.userScore.text = scoreList[position].score.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.high_scsore_display, parent, false)
        return HighScoreViewHolder(view)
    }

    class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById<TextView>(R.id.user_name)
        var userScore: TextView = itemView.findViewById<TextView>(R.id.user_score)
    }
}