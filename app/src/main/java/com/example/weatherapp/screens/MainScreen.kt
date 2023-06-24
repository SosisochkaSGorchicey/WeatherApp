package com.example.weatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.screens.ui_for_screens.BackgroundImage
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.MainBlue
import com.example.weatherapp.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.getData()
        delay(1500)
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    val internetMessage = remember { mutableStateOf("") }

    viewModel.errorMessageForDisplay.observe(LocalLifecycleOwner.current) {
        if (it.isNotEmpty()) {
            internetMessage.value = "No Internet connection"
        } else {
            internetMessage.value = ""
        }
    }

    val mainScreenTemp = rememberSaveable {
        mutableStateOf("")
    }

    val mainScreenHumidity = rememberSaveable {
        mutableStateOf("")
    }

    val mainScreenWind = rememberSaveable {
        mutableStateOf("")
    }

    val mainScreenPressure = rememberSaveable {
        mutableStateOf("")
    }

    viewModel.dataForMain.observe(LocalLifecycleOwner.current) {
        val tempInK = it.main?.temp
        val tempInC = tempInK?.minus(273.15)?.roundToInt()
        mainScreenTemp.value = tempInC.toString() + " °C"

        mainScreenHumidity.value = "Humidity: " + it.main?.humidity.toString() + " %"

        mainScreenWind.value = "Wind speed: " + it.wind?.speed.toString() + " m/s"

        mainScreenPressure.value = "Pressure: " + it.main?.pressure.toString() + " mmhg"
    }

    BackgroundImage()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {

        PullRefreshIndicator(
            refreshing,
            state,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = MainBlue
        )

        Column {

            if (internetMessage.value.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    text = internetMessage.value,
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Saint-Petersburg",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
            ) {

                Text(
                    text = mainScreenTemp.value,
                    fontSize = 18.sp,
                    color = DarkBlue
                )

                Text(
                    text = mainScreenHumidity.value,
                    fontSize = 18.sp,
                    color = DarkBlue
                )

                Text(
                    text = mainScreenWind.value,
                    fontSize = 18.sp,
                    color = DarkBlue
                )

                Text(
                    text = mainScreenPressure.value,
                    fontSize = 18.sp,
                    color = DarkBlue
                )

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainBlue),
                    onClick = {
                        navController.navigate("details_screen")
                    }
                ) {
                    Text(
                        text = "Details",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(end = 10.dp, bottom = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainBlue),
                    onClick = {
                        navController.navigate("forecast_screen")
                    }
                ) {
                    Text(
                        text = "Forecast",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
    


