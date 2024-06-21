package com.example.capstoneproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstoneproject.data.response.GetTweetByUser
import com.example.capstoneproject.data.response.SaveTweet
import com.example.capstoneproject.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateViewModel (private val repository: Repository) : ViewModel() {

    val predictData : LiveData<GetTweetByUser> = repository.predictData
    val isLoading : LiveData<Boolean> = repository.isLoading

    fun postPredictText(text: String){
        viewModelScope.launch {
            repository.saveTweet(text)
        }
    }
}