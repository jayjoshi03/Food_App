package com.example.practiceex.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("status") var status : String? = null,
    @SerializedName("data") var data : ArrayList<UserData> = arrayListOf(),
    @SerializedName("message") var message : String? = null
)
