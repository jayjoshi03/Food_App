package com.example.practiceex.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practiceex.R
import com.example.practiceex.adapter.UserAdapter
import com.example.practiceex.databinding.ActivityMainBinding
import com.example.practiceex.model.Response
import com.example.practiceex.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var userAdapter : UserAdapter
    private var userDate = Response()
    private var deleteMenu : Menu? = null
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(this, userDate.data) { show-> showHideDelete(show) }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        loadData()
        binding.recyclerView.adapter = userAdapter
    }

    private fun showHideDelete(show : Boolean) {
        deleteMenu!!.findItem(R.id.mDelete).isVisible = show
    }

    override fun onCreateOptionsMenu(menu : Menu?) : Boolean {
        deleteMenu = menu
        menuInflater.inflate(R.menu.custom_menu, menu)
        showHideDelete(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return super.onOptionsItemSelected(item)

    }

    private fun loadData() {
        ApiClient.init().getUserList().enqueue(object : Callback<Response> {
            override fun onResponse(call : Call<Response>, response : retrofit2.Response<Response>) {
                if(response.isSuccessful) {
                    userDate = response.body()!!
                    userAdapter.setList(userDate.data)
                }
            }

            override fun onFailure(call : Call<Response>, t : Throwable) {
                Toast.makeText(this@MainActivity, "Not Load Data!!", Toast.LENGTH_SHORT).show()
            }

        })
    }
}