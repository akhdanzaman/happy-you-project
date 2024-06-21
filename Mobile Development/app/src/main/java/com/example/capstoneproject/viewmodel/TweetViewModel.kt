package com.example.capstoneproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.repository.Repository
import com.example.capstoneproject.data.response.SaveTweet
import com.example.capstoneproject.data.response.GetTweetByUser
import kotlinx.coroutines.launch

class TweetViewModel(private val repository: Repository) : ViewModel() {

    private val _saveTweetResponse = MutableLiveData<SaveTweet>()
    val saveTweetResponse: LiveData<SaveTweet> get() = _saveTweetResponse

    private val _tweetsByUser = MutableLiveData<List<GetTweetByUser>>()
    val tweetsByUser: LiveData<List<GetTweetByUser>> get() = _tweetsByUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    fun saveTweet(text: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.saveTweet(text)
                _saveTweetResponse.value = repository.saveTweetResponse.value
                _isLoading.value = false
            } catch (e: Exception) {
                _isError.value = true
                _isLoading.value = false
            }
        }
    }

    fun getTweetsByUserId() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.getAllTweets()
                _tweetsByUser.value = repository.allTweet.value
                _isLoading.value = false
            } catch (e: Exception) {
                _isError.value = true
                _isLoading.value = false
            }
        }
    }
}
