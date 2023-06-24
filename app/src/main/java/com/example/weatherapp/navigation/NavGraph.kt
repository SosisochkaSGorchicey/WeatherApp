package com.example.weatherapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.screens.DetailsScreen
import com.example.weatherapp.screens.ForecastScreen
import com.example.weatherapp.screens.MainScreen
import com.example.weatherapp.screens.SplashScreen
import com.example.weatherapp.viewmodel.MainViewModel

@Composable
fun Navigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash_screen") {

        composable("splash_screen") {
            SplashScreen(navController)
        }

        composable("main_screen") {
            MainScreen(navController, viewModel)
        }

        composable("details_screen") {
            DetailsScreen(navController, viewModel)
        }

        composable("forecast_screen") {
            ForecastScreen(navController, viewModel)
        }

    }
}