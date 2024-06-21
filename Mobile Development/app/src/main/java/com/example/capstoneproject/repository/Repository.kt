package com.example.capstoneproject.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.capstoneproject.data.api.ApiConfig
import com.example.capstoneproject.data.model.UserModel
import com.example.capstoneproject.data.request.SaveTweetRequest
import com.example.capstoneproject.data.response.GetTweetByUser
import com.example.capstoneproject.data.response.LoginUser
import com.example.capstoneproject.data.response.SaveTweet
import com.example.capstoneproject.data.response.TweetsItem
import com.example.capstoneproject.utils.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse


class Repository private constructor(
    private val preferences: UserPreferences
) {

    private val _predictData = MutableLiveData<GetTweetByUser>()
    val predictData: LiveData<GetTweetByUser> = _predictData

    private val _loginData = MutableLiveData<LoginUser>()
    val loginData: LiveData<LoginUser> = _loginData

    private val _detailTweet = MutableLiveData<GetTweetByUser>()
    val detailTweet: LiveData<GetTweetByUser> = _detailTweet

    private val _allTweet = MutableLiveData<List<GetTweetByUser>>()
    val allTweet: LiveData<List<GetTweetByUser>> = _allTweet

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _saveTweetResponse = MutableLiveData<SaveTweet>()
    val saveTweetResponse: LiveData<SaveTweet> = _saveTweetResponse

    suspend fun getAllTweets(): List<TweetsItem> {
        return withContext(Dispatchers.IO) {
            val user = preferences.getUser().firstOrNull()
            if (user != null) {
                val response = ApiConfig.getApiService().getTweetsByUserId("Bearer ${user.token}", user.userId)
                response.awaitResponse().body()?.tweets ?: emptyList<TweetsItem>()
            } else {
                emptyList()
            }
        }
    }


    suspend fun saveTweet(text: String) {
        _isLoading.value = true
        val saveTweetRequest = SaveTweetRequest(text) // Menggunakan SaveTweetRequest untuk mengirim teks tweet
        Log.d("TAG", saveTweetRequest.toString())
        val token = preferences.getUser().firstOrNull()?.token
        if (token != null) {
            Log.d("TAG", saveTweetRequest.toString())
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("text", saveTweetRequest.text)
                .build();
            val client = ApiConfig.getApiService().saveTweet(requestBody, "$token")
            client.enqueue(object : Callback<SaveTweet> {
                override fun onResponse(call: Call<SaveTweet>, response: Response<SaveTweet>) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _saveTweetResponse.value = response.body()
                    } else {
                        _isError.value = true
                    }
                }

                override fun onFailure(call: Call<SaveTweet>, t: Throwable) {
                    _isLoading.value = false
                    _isError.value = true
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun postLogin(email: String, password: String) {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().postlogin(email, password)
        client.enqueue(object : Callback<LoginUser> {
            override fun onResponse(call: Call<LoginUser>, response: Response<LoginUser>) {
                _isLoading.value = true
                if (response.isSuccessful && response.body() != null) {
                    _isLoading.value = false
                    _loginData.value = response.body()
                } else {
                    _isLoading.value = false
                    _isError.value = true
                }
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }


    //    fun predictText(text: String) {
    //        _isLoading.value = true
    //        val client = ApiConfig.getApiService().predictEmotion(PredictRequest(text))
    //        client.enqueue(object : Callback<PredictResponse> {
    //            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
    //                _isLoading.value = false
    //                if (response.isSuccessful && response.body() != null) {
    //                    _predictData.value = response.body()
    //                } else {
    //                    _isError.value = true
    //                }
    //            }
    //
    //            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
    //                _isLoading.value = false
    //                _isError.value = true
    //                Log.e(TAG, "onFailure: ${t.message.toString()}")
    //            }
    //        })
    //    }



    fun getUser(): LiveData<UserModel> {
        return preferences.getUser().asLiveData()
    }

    suspend fun saveUser(user: UserModel) {
        preferences.saveUser(user)
    }

    suspend fun logout() {
        preferences.logout()
    }

    companion object {
        private const val TAG = "Repository"

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(preferences: UserPreferences): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(preferences)
            }.also { INSTANCE = it }
    }
}
