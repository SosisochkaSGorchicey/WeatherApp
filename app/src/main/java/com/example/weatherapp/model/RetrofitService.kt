package com.example.weatherapp.model


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val MY_KEY = "d9e6fe2ca9bd114df14262b014663852"

interface RetrofitService {


    @GET("forecast")
    suspend fun getDataForDays(
        @Query("lat") lat: String = "59.9386",
        @Query("lon") lon: String = "30.3141",
        @Query("appid") appid: String = MY_KEY,
    ): Response<WeatherDataForDays>

    @GET("weather")
    suspend fun getDataForMain(
        @Query("lat") lat: String = "59.9386",
        @Query("lon") lon: String = "30.3141",
        @Query("appid") appid: String = MY_KEY,
    ): Response<WeatherData>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}