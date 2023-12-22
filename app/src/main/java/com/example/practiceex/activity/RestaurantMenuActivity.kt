package com.example.practiceex.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.practiceex.adapter.MenuListAdapter
import com.example.practiceex.databinding.ActivityRestaurantMenuBinding
import com.example.practiceex.model.Menus
import com.example.practiceex.model.RestaurantData

class RestaurantMenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantMenuBinding
    private lateinit var menuList : ArrayList<Menus>
    private lateinit var itemsInCartList : ArrayList<Menus>
    private lateinit var menuAdapter : MenuListAdapter
    var totalItemInCart = 0
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemsInCartList = ArrayList()
        val dataList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DATALIST", RestaurantData::class.java)
        } else
            intent.getParcelableExtra<RestaurantData>("DATALIST")

        supportActionBar!!.apply {
            title = dataList!!.name
            subtitle = dataList.address
            setDisplayHomeAsUpEnabled(true)
        }

        menuList = dataList!!.menus
        initRecyclerView()

        val someActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            if(result.resultCode == Activity.RESULT_OK) {
                // Handle the result if needed
                // This block will be executed when the activity launched with someActivityResultLauncher finishes
                finish()
            }
        }

        binding.btnCheckout.setOnClickListener {
            if(itemsInCartList != null && itemsInCartList.size >= 0) {
                Toast.makeText(this, "Please Select at list one item", Toast.LENGTH_SHORT).show()
            }
            dataList.menus = itemsInCartList
            val intent = Intent(this, PlaceYourOrderActivity::class.java)
            intent.putExtra("DATALIST", dataList)
            someActivityResultLauncher.launch(intent)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerViewMenu.layoutManager = GridLayoutManager(this, 2)
        menuAdapter = MenuListAdapter(this, menuList)
        binding.recyclerViewMenu.adapter = menuAdapter

        menuAdapter.setOnItemClickListener(object : MenuListAdapter.OnItemCLickListener {
            override fun onAddToCartClick(index : Int, menus : Menus) {
                itemsInCartList.add(menus)
                totalItemInCart = itemsInCartList.sumBy { it.totalInCart.toInt() } ?: 0
                binding.btnCheckout.text = "Checkout ( $totalItemInCart ) items"
            }

            override fun onUpdateCartClick(index : Int, menus : Menus) {
                if(itemsInCartList.contains(menus)) {
                    val pos = itemsInCartList.indexOf(menus)
                    itemsInCartList.remove(menus)
                    pos.let { itemsInCartList.add(it, menus) }

                    totalItemInCart = itemsInCartList.sumBy { it.totalInCart.toInt() } ?: 0
                    binding.btnCheckout.text = "Checkout ( $totalItemInCart ) items"
                }
            }

            override fun onRemoveFromCartClick(index : Int, menus : Menus) {
                if(itemsInCartList.contains(menus)) {
                    itemsInCartList.remove(menus)
                    totalItemInCart = itemsInCartList.sumBy { it.totalInCart.toInt() } ?: 0
                    binding.btnCheckout.text = "Checkout ( $totalItemInCart ) items"
                }
            }
        })
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when(item.itemId) {
            androidx.appcompat.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp() : Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}