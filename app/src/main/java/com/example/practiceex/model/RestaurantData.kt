package com.example.practiceex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Parcelize
data class RestaurantData(
    @SerializedName("name") var name : String? = null,
    @SerializedName("address") var address : String? = null,
    @SerializedName("delivery_charge") var deliveryCharge : Float? = null,
    @SerializedName("image") var image : String? = null,
    @SerializedName("hours") var hours : Hours? = Hours(),
    @SerializedName("menus") var menus : ArrayList<Menus> = arrayListOf(),
) : Parcelable

@Parcelize
data class Hours(
    @SerializedName("Sunday") var sunday : String? = null,
    @SerializedName("Monday") var monday : String? = null,
    @SerializedName("Tuesday") var tuesday : String? = null,
    @SerializedName("Wednesday") var wednesday : String? = null,
    @SerializedName("Thursday") var thursday : String? = null,
    @SerializedName("Friday") var friday : String? = null,
    @SerializedName("Saturday") var saturday : String? = null,
) : Parcelable {
    fun getTodayHours() : String {
        val calendar = Calendar.getInstance()
        val date : Date = calendar.time
        val days = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date)
        return when(days) {
            "Sunday" -> sunday ?: ""
            "Monday" -> monday ?: ""
            "Tuesday" -> tuesday ?: ""
            "Wednesday" -> wednesday ?: ""
            "Thursday" -> thursday ?: ""
            "Friday" -> friday ?: ""
            "Saturday" -> saturday ?: ""
            else -> sunday ?: ""
        }
    }
}

@Parcelize
data class Menus(
    @SerializedName("name") var name : String? = null,
    @SerializedName("price") var price : Float? = null,
    @SerializedName("url") var url : String? = null,
    var totalInCart : Long = 0,
) : Parcelable
