package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("sys") var sys: Sys? = Sys(),
)


data class Weather(
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
)

data class Main(
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("temp_min") var tempMin: Double? = null,
    @SerializedName("temp_max") var tempMax: Double? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null
)

data class Wind(
    @SerializedName("speed") var speed: Int? = null,
)

data class Sys(
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null
)