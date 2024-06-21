package com.example.capstoneproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.response.TweetsItem
import com.example.capstoneproject.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
): ViewModel() {
    private val _tweet = MutableStateFlow<TweetsItem?>(null)
    val tweet = _tweet.asStateFlow()

    fun getTweet(tweetID: String?) {
        if (tweetID != null) {
            viewModelScope.launch {
                val result = repository.getAllTweets()
                _tweet.value = result.firstOrNull { tweetID == it.tweetId }
            }
        }
    }
}