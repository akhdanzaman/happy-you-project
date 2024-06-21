package com.example.capstoneproject.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.R
import com.example.capstoneproject.adapter.TweetAdapter
import com.example.capstoneproject.data.response.GetTweetByUser
import com.example.capstoneproject.data.response.TweetsItem
import com.example.capstoneproject.databinding.ActivityCreateBinding
import com.example.capstoneproject.databinding.ActivityMainBinding
import com.example.capstoneproject.repository.Repository
import com.example.capstoneproject.ui.detail.DetailActivity
import com.example.capstoneproject.ui.post.CreateActivity
import com.example.capstoneproject.ui.profile.ProfileActivity
import com.example.capstoneproject.utils.Injection
import com.example.capstoneproject.utils.factory.ViewModelFactory
import com.example.capstoneproject.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onResume() {
        super.onResume()
        factory = ViewModelFactory.getInstance(this)
        mainViewModel.refreshNews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        setupView()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        mainViewModel.allNews.observe(this) { tweets ->
            binding.rvTips.adapter = TweetAdapter(tweets, onItemClick = {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("tweet_id", it.tweetId)
                startActivity(intent)
            })
            binding.rvTips.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun setupView() {
        val fab: FloatingActionButton = findViewById(R.id.scan_btn)
        fab.setOnClickListener {
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

}
