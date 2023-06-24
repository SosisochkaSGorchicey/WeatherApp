package com.example.weatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.getDateTime
import com.example.weatherapp.screens.ui_for_screens.BackgroundImage
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.MainBlue
import com.example.weatherapp.viewmodel.MainViewModel
import kotlin.math.roundToInt

@Composable
fun DetailsScreen(navController: NavController, viewModel: MainViewModel) {

    val detailScreenDesc = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenTemp = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenFeelsLike = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenHumidity = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenWind = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenPressure = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenSunrise = rememberSaveable {
        mutableStateOf("")
    }

    val detailScreenSunset = rememberSaveable {
        mutableStateOf("")
    }


    viewModel.dataForMain.observe(LocalLifecycleOwner.current) {
        detailScreenDesc.value = it.weather[0].description.toString()

        val tempInK = it.main?.temp
        val tempInC = tempInK?.minus(273.15)?.roundToInt()
        detailScreenTemp.value = tempInC.toString() + " °C"

        val tempFeelInK = it.main?.feelsLike
        val tempFeelInC = tempFeelInK?.minus(273.15)?.roundToInt()
        detailScreenFeelsLike.value = "fells like $tempFeelInC °C"

        detailScreenHumidity.value = "humidity: " + it.main?.humidity.toString() + " %"

        detailScreenWind.value = "wind speed: " + it.wind?.speed.toString() + " m/s"

        detailScreenPressure.value = "pressure: " + it.main?.pressure.toString() + " mmhg"


        val timesunrise = it.sys?.sunrise.toString()
        val timesunrise2 = getDateTime(timesunrise)
        detailScreenSunrise.value = "sunrise at " + timesunrise2.toString()

        val timesunset = it.sys?.sunset.toString()
        val timesunset2 = getDateTime(timesunset)
        detailScreenSunset.value = "sunset at " + timesunset2.toString()
    }


    BackgroundImage()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
    ) {

        Text(
            modifier = Modifier.padding(5.dp),
            text = "Saint-Petersburg",
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            text = detailScreenDesc.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenTemp.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenFeelsLike.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenHumidity.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenWind.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenPressure.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenSunrise.value,
            fontSize = 18.sp,
            color = DarkBlue
        )

        Text(
            text = detailScreenSunset.value,
            fontSize = 18.sp,
            color = DarkBlue
        )
    }


    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        FloatingActionButton(
            modifier = Modifier.padding(8.dp),
            onClick = {
                navController.navigate("main_screen") {
                    popUpTo(0)
                }
            },
            containerColor = MainBlue,
            contentColor = Color.White,
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )

    }

}