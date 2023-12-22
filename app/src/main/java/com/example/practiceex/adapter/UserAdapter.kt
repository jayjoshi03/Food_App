package com.example.practiceex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.practiceex.R
import com.example.practiceex.databinding.CardViewBinding
import com.example.practiceex.model.UserData

class UserAdapter(private var context : Context, private var userList : ArrayList<UserData>, var onDeleteClick : (Boolean) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private lateinit var binding : CardViewBinding
    private var selectedItem = mutableListOf<Int>()
    private var isSelectionModeEnabled = false

    class UserViewHolder(val binding : CardViewBinding) : RecyclerView.ViewHolder(binding.root)

    fun setList(user : ArrayList<UserData>) {
        this.userList = user
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : UserViewHolder {
        binding = CardViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount() : Int {
        return userList.size
    }

    override fun onBindViewHolder(holder : UserViewHolder, position : Int) {
        val user = userList[position]
        holder.binding.apply {
            textView1.text = user.employeeName
            textView2.text = user.employeeSalary.toString()
            textView3.text = user.employeeAge.toString()
            val pos = position + 1
            textView4.text = pos.toString()

            card.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if(user.check) R.color.selected_color
                    else android.R.color.transparent
                )
            )

            card.setOnLongClickListener {
                if(!isSelectionModeEnabled) {
                    isSelectionModeEnabled = true
                    selectedItem.clear()
                    markSelectedItem(position)
                    notifyItemChanged(position)
                }
                true
            }
            card.setOnClickListener {
                if(isSelectionModeEnabled) {
                    toggleItemSelection(position)
                    notifyItemChanged(position)
                } else {
                    // Handle single click action
                    // Implement your single-click behavior here
                }
            }
        }
    }

    private fun toggleItemSelection(position : Int) {
        if(selectedItem.contains(position)) {
            userList[position].check = false
            selectedItem.remove(position)
        } else {
            userList[position].check = true
            selectedItem.add(position)
        }
        onDeleteClick(selectedItem.isNotEmpty())

        if (selectedItem.isEmpty()) {
            isSelectionModeEnabled = false
        }
    }

    private fun deSelectedItem(position : Int) {
        if(selectedItem.contains(position)) {
            selectedItem.remove(position)
            userList[position].check = false
            notifyDataSetChanged()
            onDeleteClick(selectedItem.isNotEmpty())
        }
    }

    fun deleteSelectedItem() {
        if(selectedItem.isNotEmpty()) {
            userList.removeAll { item-> item.check == true }
        }
        notifyDataSetChanged()
    }

    private fun markSelectedItem(position : Int) {
        for(i in userList) {
            i.check = false
        }
        selectedItem.add(position)
        selectedItem.forEach {
            userList[it].check = true
        }
        onDeleteClick(true)
    }
}