package com.example.practiceex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceex.databinding.AutoLayoutBinding

class AutoSwipeAdapter(private val context : Context) : RecyclerView.Adapter<AutoSwipeAdapter.AutoViewHolder>() {
    private lateinit var binding : AutoLayoutBinding

    inner class AutoViewHolder(var binding : AutoLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : AutoViewHolder {
        binding = AutoLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return AutoViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return 5
    }

    override fun onBindViewHolder(holder : AutoViewHolder, position : Int) {
        val pos = position + 1
        holder.binding.text.text = pos.toString()
    }
}