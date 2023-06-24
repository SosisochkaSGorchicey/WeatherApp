package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherDataForDays(
    @SerializedName("list") var list: ArrayList<ListForDays> = arrayListOf(),
)


data class MainForDays(
    @SerializedName("temp") var temp: Double? = null,
)

data class ListForDays(
    @SerializedName("main") var main: MainForDays? = MainForDays(),
    @SerializedName("dt_txt") var dtTxt: String? = null
)








