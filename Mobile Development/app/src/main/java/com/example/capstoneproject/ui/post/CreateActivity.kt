package com.example.capstoneproject.ui.post

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.capstoneproject.databinding.ActivityCreateBinding
import com.example.capstoneproject.utils.factory.ViewModelFactory
import com.example.capstoneproject.viewmodel.CreateViewModel

class CreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private lateinit var factory: ViewModelFactory
    private val createViewModel: CreateViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        setupView()
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.postButton.setOnClickListener {
            val text = binding.statusEditText.text.toString().trim()

            createViewModel.postPredictText(text)
            finish()
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        supportActionBar?.hide()
    }
}