package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.MainRepository
import com.example.weatherapp.model.RetrofitService
import com.example.weatherapp.navigation.Navigation
import com.example.weatherapp.viewmodel.MainViewModel
import com.example.weatherapp.viewmodel.MyViewModelFactory

class MainActivity : ComponentActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)

        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        ).get(MainViewModel::class.java)

        if (viewModel.dataHasBeenLoaded.value == false) {
            viewModel.getData()
        }

        setContent {
            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                Navigation(viewModel)
            }
        }
    }
}

