package com.example.capstoneproject.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.capstoneproject.databinding.ActivityDetailBinding
import com.example.capstoneproject.utils.factory.ViewModelFactory
import com.example.capstoneproject.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tweetId = intent.getStringExtra("tweet_id")

        viewModel.getTweet(tweetId)
        setupTweet()
    }

    private fun setupTweet() {
        lifecycleScope.launch {
            viewModel.tweet.collectLatest { tweet ->
                if (tweet != null) {
                    binding.tvScanDescription.text = tweet.text
                    binding.tvMentalHealthStatus.text = tweet.mentalState
                    binding.tvRekomendasiKegiatan.text = tweet.message
                    binding.tvCaraMerawat.text = tweet.saran
                }
            }
        }
    }
}