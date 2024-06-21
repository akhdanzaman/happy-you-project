package com.example.capstoneproject.data.response

import com.google.gson.annotations.SerializedName

data class GetTweetByUser(

	@field:SerializedName("tweets")
	val tweets: List<TweetsItem>
)

data class TweetsItem(

	@field:SerializedName("mental_state")
	val mentalState: String,

	@field:SerializedName("saran")
	val saran: String,

	@field:SerializedName("tweet_id")
	val tweetId: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("message")
	val message: String
)
