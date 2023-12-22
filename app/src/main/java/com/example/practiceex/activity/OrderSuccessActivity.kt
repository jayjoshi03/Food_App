package com.example.practiceex.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practiceex.databinding.ActivityOrderSuccessBinding
import com.example.practiceex.model.RestaurantData

class OrderSuccessActivity : AppCompatActivity() {
    lateinit var binding : ActivityOrderSuccessBinding
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DATALIST", RestaurantData::class.java)
        } else
            intent.getParcelableExtra<RestaurantData>("DATALIST")

        supportActionBar!!.apply {
            title = dataList!!.name
            subtitle = dataList.address
            setDisplayHomeAsUpEnabled(false)
        }

        binding.btnDone.setOnClickListener {
            startActivity(Intent(this,FoodScreenActivity::class.java))
            finish()
        }
    }
}