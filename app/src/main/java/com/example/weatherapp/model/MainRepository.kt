package com.example.weatherapp.model

class MainRepository(private val retrofitService: RetrofitService) {
    suspend fun getDataForDays() = retrofitService.getDataForDays()
    suspend fun getDataForMain() = retrofitService.getDataForMain()
}