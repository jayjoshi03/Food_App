package com.example.practiceex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceex.databinding.FoodCardBinding
import com.example.practiceex.model.RestaurantData

class FoodItemAdapter(private var context : Context, private var restaurantList : ArrayList<RestaurantData>) : RecyclerView.Adapter<FoodItemAdapter.FoodViewHolder>() {
    private lateinit var binding : FoodCardBinding
    private lateinit var listener : OnItemCLickListener

    fun updateList(restaurantList : ArrayList<RestaurantData>) {
        this.restaurantList = restaurantList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(updateListener : OnItemCLickListener) {
        this.listener = updateListener
    }

    inner class FoodViewHolder(var binding : FoodCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : FoodViewHolder {
        binding = FoodCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder : FoodViewHolder, position : Int) {
        val dataList = restaurantList[position]
        holder.binding.apply {
            restaurantName.text = dataList.name
            restaurantAddress.text = "Address: ${dataList.address}"
            restaurantHours.text = "Today's hours: ${dataList.hours!!.getTodayHours()}"
            holder.itemView.setOnClickListener {
                listener.onItemClickListener(position, dataList)
            }
            Glide.with(thumbImage)
                .load(dataList.image)
                .centerCrop()
                .into(thumbImage)
        }
    }

    interface OnItemCLickListener {
        fun onItemClickListener(index : Int, restaurantData : RestaurantData)
    }
}