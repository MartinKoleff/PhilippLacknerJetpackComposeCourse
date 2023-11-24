package com.koleff.philipplacknerjetpackcomposecourse.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue //Add these to remove error by IDE
import androidx.compose.runtime.setValue //Add these to remove error by IDE
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun MainScreen() {
    var points: Int by remember {
        mutableStateOf(0)
    }

    var isTimerRunning: Boolean by remember {
        mutableStateOf(false)
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Points: $points",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Button(onClick = {
                isTimerRunning = !isTimerRunning
                points = 0
            }) {
                Text(
                    text = if(isTimerRunning) "Reset" else "Start",
                    color = Color.Red,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            CountdownTimer(isTimerRunning) {
                isTimerRunning = false
            }
        }
        CirclePicker(
            enabled = isTimerRunning
        ){
            points++
        }
    }
}

@Composable
fun CountdownTimer(
    isTimerRunning: Boolean = false,
    time: Int = 30000, //time in ms (1000 ms = 1 sec)
    onTimerEnd: () -> Unit = {}
) {
    var currentTime by remember {
        mutableStateOf(time)
    }

    //When key is updated -> coroutine is canceled and starts again with new values.
    LaunchedEffect(key1 = isTimerRunning, key2 = currentTime) {
        if (!isTimerRunning) {
            currentTime = time
            return@LaunchedEffect
        }

        if (currentTime > 0) {
            delay(1000)
            currentTime -= 1000
        } else {
            onTimerEnd.invoke()
        }
    }

    Text(
        text = (currentTime / 1000).toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}