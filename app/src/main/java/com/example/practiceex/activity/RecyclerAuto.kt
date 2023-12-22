package com.example.practiceex.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceex.adapter.AutoSwipeAdapter
import com.example.practiceex.databinding.ActivityRecyclerAutoBinding
import java.util.Timer
import java.util.TimerTask


class RecyclerAuto : AppCompatActivity() {
    lateinit var binding : ActivityRecyclerAutoBinding
    lateinit var autoAdapter : AutoSwipeAdapter
    val linear = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerAutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setRV()
    }

    private fun setRV() {
        autoAdapter = AutoSwipeAdapter(this)
        binding.recyclerViewAuto.layoutManager = linear
        binding.recyclerViewAuto.adapter = autoAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewAuto)

        binding.recyclerViewAuto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = linear.childCount
                val totalItemCount = linear.itemCount
                val firstVisibleItem = linear.findFirstVisibleItemPosition()

                val threshold = 5

                if (visibleItemCount + firstVisibleItem >= totalItemCount - threshold) {
                    // Load more data here if needed
                    // For instance: autoAdapter.loadMoreData()
                }
            }
        })

        val time = Timer()
        time.schedule(object : TimerTask() {
            override fun run() {
                val lastVisibleItem = linear.findLastCompletelyVisibleItemPosition()
                if(lastVisibleItem < (autoAdapter.itemCount - 1)) {
                    linear.smoothScrollToPosition(binding.recyclerViewAuto,RecyclerView.State(),lastVisibleItem + 1)
                }else if(lastVisibleItem == (autoAdapter.itemCount - 1)) {
                    linear.smoothScrollToPosition(binding.recyclerViewAuto,RecyclerView.State(),0)
                }
            }
        }, 0, 3000)
    }
}