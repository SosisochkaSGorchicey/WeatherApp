package com.example.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.MainRepository
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.model.WeatherDataForDays
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val dataHasBeenLoaded = MutableLiveData(false)
    val errorMessage = MutableLiveData<String>()
    val errorMessageForDisplay = MutableLiveData<String>()
    val dataForDays = MutableLiveData<WeatherDataForDays>()
    val dataForMain = MutableLiveData<WeatherData>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorMessageForDisplay.postValue(throwable.localizedMessage)
    }

    fun getData() {

        dataHasBeenLoaded.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val responseForDays = mainRepository.getDataForDays()
            val responseForMain = mainRepository.getDataForMain()

            if (responseForDays.isSuccessful && responseForMain.isSuccessful) {
                errorMessageForDisplay.postValue("")
                dataForDays.postValue(responseForDays.body())
                dataForMain.postValue(responseForMain.body())
                loading.postValue(false)
            } else {
                onError("Error : ${responseForMain.message()} ")
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}