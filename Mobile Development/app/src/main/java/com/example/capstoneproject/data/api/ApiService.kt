package com.example.capstoneproject.data.api

import com.example.capstoneproject.data.model.ApiResponse
import com.example.capstoneproject.data.request.SaveTweetRequest
import com.example.capstoneproject.data.response.LoginUser
import com.example.capstoneproject.data.response.GetTweetByUser
import com.example.capstoneproject.data.response.RegisterUser
import com.example.capstoneproject.data.response.SaveTweet
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("/auth/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("/auth/login")
    fun postlogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginUser>

    @POST("/save-tweet")
    fun saveTweet(
        @Body saveTweetRequest: RequestBody,
        @Header("Authorization") token: String,
    ): Call<SaveTweet>

    @GET("/get-tweet-byuserid")
    fun getTweetsByUserId(
        @Header("Authorization") token: String,
        @Query("user_id") userId: String
    ): Call<GetTweetByUser>


}
