package com.example.capstoneproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.response.GetTweetByUser
import com.example.capstoneproject.data.response.TweetsItem
import com.example.capstoneproject.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel()  {

    private val _allNews = MutableLiveData<List<TweetsItem>>()
    val allNews: LiveData<List<TweetsItem>> = _allNews

    init {
        getAllNews()
    }

    fun refreshNews() {
        viewModelScope.launch {
            delay(3000L)
            val result = repository.getAllTweets()
            _allNews.value = result
        }
    }

    fun getAllNews() {
        viewModelScope.launch {
            val result = repository.getAllTweets()
            _allNews.value = result
        }
    }
}