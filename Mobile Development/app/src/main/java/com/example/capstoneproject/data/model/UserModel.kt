package com.example.capstoneproject.data.model

// UserModel for representing user data in the app
data class UserModel(
    val name: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean
)

// RegisterRequest data class for registering a new user
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

// LoginRequest data class for logging in a user
data class LoginRequest(
    val email: String,
    val password: String
)

// UserData data class for storing user details
data class UserData(
    val user_id: String,
    val name: String,
    val email: String
    val status: Boolean
)

// ApiResponse data class for representing API response structure
data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: UserData? = null,
    val token: String? = null
)
