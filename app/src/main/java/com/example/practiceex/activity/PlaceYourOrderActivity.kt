package com.example.practiceex.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiceex.adapter.PlaceOrderAdapter
import com.example.practiceex.databinding.ActivityPlaceYourOrderBinding
import com.example.practiceex.model.RestaurantData

class PlaceYourOrderActivity : AppCompatActivity() {
    lateinit var binding : ActivityPlaceYourOrderBinding
    private var isDeliveryOn = false
    private var isCheckPlace = true
    private lateinit var menuAdapter : PlaceOrderAdapter
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceYourOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataList = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DATALIST", RestaurantData::class.java)
        } else
            intent.getParcelableExtra<RestaurantData>("DATALIST")

        supportActionBar!!.apply {
            title = dataList!!.name
            subtitle = dataList.address
            setDisplayHomeAsUpEnabled(true)
        }

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            if(result.resultCode == Activity.RESULT_OK) {
                // Handle the result if needed
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        binding.apply {
            btnPlaceOrder.setOnClickListener {
                onPlaceOrderClick(dataList)
                //Data Transfer
                if(isCheckPlace) {
                    val intent = Intent(this@PlaceYourOrderActivity, OrderSuccessActivity::class.java)
                    intent.putExtra("DATALIST", dataList)
                    startForResult.launch(intent)
                }else{
                    onPlaceOrderClick(dataList)
                }
            }

            switchDelivery.setOnCheckedChangeListener { compoundButton, isCheck->
                if(isCheck) {
                    isDeliveryOn = true
                    inputAddress.visibility = View.VISIBLE
                    inputCity.visibility = View.VISIBLE
                    inputState.visibility = View.VISIBLE
                    inputZip.visibility = View.VISIBLE
                    tvDeliveryCharge.visibility = View.VISIBLE
                    tvDeliveryChargeAmount.visibility = View.VISIBLE
                    calculateTotalAmount(dataList)
                } else {
                    isDeliveryOn = false
                    inputAddress.visibility = View.GONE
                    inputCity.visibility = View.GONE
                    inputState.visibility = View.GONE
                    inputZip.visibility = View.GONE
                    tvDeliveryCharge.visibility = View.GONE
                    tvDeliveryChargeAmount.visibility = View.GONE
                    calculateTotalAmount(dataList)
                }
            }

            calculateTotalAmount(dataList)
        }

        initRecyclerView(dataList)
    }

    private fun calculateTotalAmount(dataList : RestaurantData?) {
        var subTotalAmount = 0f

        dataList?.menus?.forEach { m->
            subTotalAmount += m.price!!.times(m.totalInCart.toFloat())
        }

        binding.tvSubTotalAmount.text = "$${"%.2f".format(subTotalAmount)}"
        if(isDeliveryOn) {
            binding.tvDeliveryChargeAmount.text = "$${"%.2f".format(dataList?.deliveryCharge)}"
            subTotalAmount += dataList?.deliveryCharge ?: 0f
        }
        binding.tvTotalAmount.text = "$${"%.2f".format(subTotalAmount)}"
    }

    private fun onPlaceOrderClick(dataList : RestaurantData?) : Boolean {
        binding.apply {
            isCheckPlace = validateInput(inputName, "Please Enter Name")
            if(isDeliveryOn) {
                isCheckPlace = validateInput(inputAddress, "Please Enter Address") && isCheckPlace
                isCheckPlace = validateInput(inputCity, "Please Enter City") && isCheckPlace
                isCheckPlace = validateInput(inputState, "Please Enter State") && isCheckPlace
                isCheckPlace = validateInput(inputZip, "Please Enter Zip") && isCheckPlace
            }
            isCheckPlace = validateInput(inputCardNumber, "Please Enter Card Number") && isCheckPlace
            isCheckPlace = validateInput(inputCardExpiry, "Please Enter Card Expiry") && isCheckPlace
            isCheckPlace = validateInput(inputCardPin, "Please Enter Card Pin") && isCheckPlace
        }
        return isCheckPlace
    }

    private fun initRecyclerView(dataList : RestaurantData?) {
        binding.cardItemRecyclerView.layoutManager = LinearLayoutManager(this)
        menuAdapter = PlaceOrderAdapter(this, dataList!!.menus)
        binding.cardItemRecyclerView.adapter = menuAdapter
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        when(item.itemId) {
            androidx.appcompat.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun validateInput(input : EditText, errorMessage : String) : Boolean {
        return if(TextUtils.isEmpty(input.text.toString())) {
            input.error = errorMessage
            false
        } else {
            true
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onSupportNavigateUp() : Boolean {
        setResult(Activity.RESULT_CANCELED)
        finish()
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}