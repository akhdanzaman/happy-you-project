package com.example.capstoneproject.data.response

import com.google.gson.annotations.SerializedName

data class SaveTweet(

	@field:SerializedName("tweet_id")
	val tweetId: String,

	@field:SerializedName("text")
	val text: String,

	@field:SerializedName("status")
	val status: String
)
