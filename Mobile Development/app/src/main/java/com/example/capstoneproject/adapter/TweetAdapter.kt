package com.example.capstoneproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.data.response.GetTweetByUser
import com.example.capstoneproject.data.response.TweetsItem

class TweetAdapter(
    private val tweets: List<TweetsItem>,
    private val onItemClick: (TweetsItem) -> Unit
) : RecyclerView.Adapter<TweetAdapter.TweetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return TweetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        val tweet = tweets[position]
        Log.d("TAG1", tweet.toString())
        holder.bind(tweet)
        holder.itemView.setOnClickListener { onItemClick(tweet) }
    }

    override fun getItemCount(): Int = tweets.size

    class TweetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tweetTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(tweet: TweetsItem) {
            tweetTextView.text = tweet.text
        }
    }
}
