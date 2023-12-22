package com.example.practiceex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceex.databinding.PlaceOrederCardBinding
import com.example.practiceex.model.Menus

class PlaceOrderAdapter(private var context : Context, private var menuList : ArrayList<Menus>) : RecyclerView.Adapter<PlaceOrderAdapter.MenuViewHolder>() {
    private lateinit var binding : PlaceOrederCardBinding

    fun updateList(menuList : ArrayList<Menus>) {
        this.menuList = menuList
        notifyDataSetChanged()
    }

    inner class MenuViewHolder(var binding : PlaceOrederCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MenuViewHolder {
        binding = PlaceOrederCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder : MenuViewHolder, position : Int) {
        val dataList = menuList[position]
        holder.binding.apply {
            menuName.text = dataList.name
            menuPrice.text = "Price: $${"%.2f".format(dataList.price?.times(dataList.totalInCart.toFloat()))}"
            menuQty.text = "Qty: ${dataList.totalInCart}"

            Glide.with(thumbImage)
                .load(dataList.url)
                .centerCrop()
                .into(thumbImage)
        }
    }
}