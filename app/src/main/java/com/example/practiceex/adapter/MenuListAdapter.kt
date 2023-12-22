package com.example.practiceex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practiceex.databinding.MenuCardBinding
import com.example.practiceex.model.Menus

class MenuListAdapter(private var context : Context, private var menuList : ArrayList<Menus>) : RecyclerView.Adapter<MenuListAdapter.MenuViewHolder>() {
    private lateinit var binding : MenuCardBinding
    private lateinit var listener : OnItemCLickListener

    fun updateList(menuList : ArrayList<Menus>) {
        this.menuList = menuList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(updateListener : OnItemCLickListener) {
        this.listener = updateListener
    }

    inner class MenuViewHolder(var binding : MenuCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MenuViewHolder {
        binding = MenuCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder : MenuViewHolder, position : Int) {
        val dataList = menuList[position]
        holder.binding.apply {
            menuName.text = dataList.name
            menuPrice.text = "Price: $${dataList.price}"

            addToCardButton.setOnClickListener {
                dataList.totalInCart = 1
                listener.onAddToCartClick(position, dataList)
                addMoreLayout.visibility = View.VISIBLE
                addToCardButton.visibility = View.GONE
                tvCount.text = dataList.totalInCart.toString()
            }

            imageMinus.setOnClickListener {
                var total = dataList.totalInCart
                total--
                if(total > 0) {
                    dataList.totalInCart = total
                    listener.onUpdateCartClick(position, dataList)
                    tvCount.text = total.toString()
                } else {
                    addMoreLayout.visibility = View.GONE
                    addToCardButton.visibility = View.VISIBLE
                    dataList.totalInCart = total
                    listener.onRemoveFromCartClick(position, dataList)
                }
            }

            imageAdd.setOnClickListener {
                var total = dataList.totalInCart
                total++
                if(total <= 10) {
                    dataList.totalInCart = total
                    listener.onUpdateCartClick(position, dataList)
                    tvCount.text = total.toString()
                }
            }

            Glide.with(thumbImage)
                .load(dataList.url)
                .centerCrop()
                .into(thumbImage)
        }
    }

    interface OnItemCLickListener {
        fun onAddToCartClick(index : Int, menus : Menus)
        fun onUpdateCartClick(index : Int, menus : Menus)
        fun onRemoveFromCartClick(index : Int, menus : Menus)
    }
}