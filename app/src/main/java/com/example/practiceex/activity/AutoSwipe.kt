package com.example.practiceex.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practiceex.databinding.ActivityAutoSwipeBinding

class AutoSwipe : AppCompatActivity() {
    private lateinit var binding : ActivityAutoSwipeBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutoSwipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}