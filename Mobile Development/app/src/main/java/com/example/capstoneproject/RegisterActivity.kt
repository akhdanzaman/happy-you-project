package com.example.capstoneproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        val registerButton: Button = findViewById(R.id.MasukButton)
        val loginTextView: TextView = findViewById(R.id.hyperlink_login_button)

        // Set click listener for register button
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Call register API using Retrofit
                makeRegisterRequest(name, email, password, 3) // Retry 3 times on failure
            } else {
                Toast.makeText(this@RegisterActivity, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Set click listener for login text view
        loginTextView.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun makeRegisterRequest(name: String, email: String, password: String, retryCount: Int) {
        // Make the API call using RetrofitClient instance
        RetrofitClient.instance.register(name, email, password).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("RegisterActivity", "Response body: $apiResponse")
                    if (apiResponse != null && apiResponse.success) {
                        // Registration successful
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Registration successful", Toast.LENGTH_SHORT).show()
                            navigateToLogin()
                        }
                    } else {
                        // Registration failed with error message
                        val errorMessage = apiResponse?.message ?: "Unknown error occurred"
                        runOnUiThread {
                            Toast.makeText(this@RegisterActivity, "Registration failed: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Failed to register with HTTP error code
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Failed to register. Response code: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("RegisterActivity", "Failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Network or unexpected errors
                if (retryCount > 0) {
                    // Retry if attempts left
                    Log.e("RegisterActivity", "Retrying... attempts left: ${retryCount - 1}")
                    makeRegisterRequest(name, email, password, retryCount - 1)
                } else {
                    // No more retries, show error message
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                    Log.e("RegisterActivity", "Error: ${t.message}", t)
                }
            }
        })
    }

    private fun navigateToLogin() {
        // Navigate to login activity
        val intent = Intent(this@RegisterActivity, LoginViewModel::class.java)
        startActivity(intent)
        finish() // Optional: Close current activity after navigating
    }
}
