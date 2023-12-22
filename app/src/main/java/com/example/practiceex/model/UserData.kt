package com.example.practiceex.model

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("employee_name") var employeeName : String? = null,
    @SerializedName("employee_salary") var employeeSalary : Int? = null,
    @SerializedName("employee_age") var employeeAge : Int? = null,
    @SerializedName("profile_image") var profileImage : String? = null,
    var check:Boolean = false
)
