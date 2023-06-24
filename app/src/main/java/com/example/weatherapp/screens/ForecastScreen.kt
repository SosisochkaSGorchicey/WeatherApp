package com.example.weatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.model.ListForDays
import com.example.weatherapp.screens.ui_for_screens.BackgroundImage
import com.example.weatherapp.ui.theme.DarkBlue
import com.example.weatherapp.ui.theme.MainBlue
import com.example.weatherapp.viewmodel.MainViewModel
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(navController: NavController, viewModel: MainViewModel) {

    val listWithAllInfo = remember {
        mutableStateOf(arrayListOf<ListForDays>())
    }

    viewModel.dataForDays.observe(LocalLifecycleOwner.current) {
        listWithAllInfo.value = it.list
    }

    val listDateForDisplay = arrayListOf<String>()
    val listTempForDisplay = arrayListOf<String>()

    listWithAllInfo.value.forEach {
        val time = it.dtTxt?.substring(11, 16)
        if (time == "00:00") {
            var date = it.dtTxt?.substringBefore(' ')
            listDateForDisplay.add(date ?: "error")
        }
        val tempInK = it.main?.temp
        val tempInC = tempInK?.minus(273.15)?.roundToInt()
        listTempForDisplay.add(it.dtTxt?.substringBefore(' ') + '\n' + time + '\n' + tempInC + "°C")

    }

    BackgroundImage()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        for (i in 0 until listDateForDisplay.size) {

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (i == 4) Modifier.padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 10.dp,
                                bottom = 75.dp
                            ) else Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(20)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {

                        Text(
                            text = listDateForDisplay[i],
                            modifier = Modifier.padding(start = 6.dp, top = 6.dp, bottom = 10.dp),
                            color = DarkBlue,
                            fontSize = 16.sp
                        )
                        LazyRow() {
                            listTempForDisplay.forEach {
                                if (it.substringBefore('\n') == listDateForDisplay[i]) {
                                    item {
                                        Card(
                                            modifier = Modifier.padding(5.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = MainBlue,
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(5.dp)
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(5.dp),
                                                    text = it.substringAfter('\n'),
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        FloatingActionButton(
            modifier = Modifier.padding(10.dp),
            onClick = {
                navController.navigate("main_screen") {
                    popUpTo(0)
                }
            },
            containerColor = DarkBlue,
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