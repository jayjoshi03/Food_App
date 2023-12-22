package com.example.practiceex.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiceex.R
import com.example.practiceex.adapter.FoodItemAdapter
import com.example.practiceex.databinding.ActivityFoodScreenBinding
import com.example.practiceex.model.RestaurantData
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class FoodScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFoodScreenBinding
    private lateinit var listAdapter : FoodItemAdapter
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Food Screen"

        val restaurantList = getRestaurantDate()
        initRecyclerView(restaurantList)
    }

    private fun initRecyclerView(dataList : ArrayList<RestaurantData>) {
        listAdapter = FoodItemAdapter(this, dataList)
        binding.recyclerViewFood.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFood.adapter = listAdapter

        listAdapter.setOnItemClickListener(object : FoodItemAdapter.OnItemCLickListener {
            override fun onItemClickListener(index : Int, restaurantData : RestaurantData) {
                val intent = Intent(applicationContext, RestaurantMenuActivity::class.java)
                intent.putExtra("DATALIST", restaurantData)
                startActivity(intent)
            }

        })
    }

    private fun getRestaurantDate() : ArrayList<RestaurantData> {
        val isStream = resources.openRawResource(R.raw.restaurent)
        val writer = StringBuilder()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(isStream, "UTF-8"))
            var n : Int
            while(reader.read(buffer).also { n = it } != -1) {
                writer.append(buffer, 0, n)
            }
        } catch(e : Exception) {
            e.printStackTrace()
        }

        val jsonStr = writer.toString()
        val gson = Gson()
        val restaurantModels = gson.fromJson(jsonStr, Array<RestaurantData>::class.java)
        return restaurantModels.toList() as ArrayList<RestaurantData>
    }

}