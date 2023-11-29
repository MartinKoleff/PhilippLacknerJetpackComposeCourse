package com.koleff.philipplacknerjetpackcomposecourse

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.koleff.philipplacknerjetpackcomposecourse.composable.Clock
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {

    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    private var hoursCounter = 15
    private var minutesCounter = 30
    private var secondsCounter = 45

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentTime by remember {
                mutableStateOf(dateTimeFormatter.format(LocalDateTime.now()))
            }

            LaunchedEffect(key1 = currentTime) {
                currentTime = startClock()
            }

            Clock(modifier = Modifier.fillMaxSize(), currentTime = currentTime)
        }
    }

    private suspend fun startClock(): String {
        delay(1000)
        return dateTimeFormatter.format(LocalDateTime.now()) //To format as hh:mm:ss
    }

    //Used for testing purposes
    private suspend fun startClockHours(): String {
        delay(1000)

        if (hoursCounter + 1 > 24) {
            hoursCounter = 0
        } else {
            hoursCounter++
        }

        return String.format("%d:%d:%d", hoursCounter, minutesCounter, secondsCounter)
    }

    private suspend fun startClockMinutes(): String {
        delay(1000)

        if (minutesCounter + 1 > 60) {
            minutesCounter = 0
        } else {
            minutesCounter++
        }

        return String.format("%d:%d:%d", hoursCounter, minutesCounter, secondsCounter)
    }

    private suspend fun startClockSeconds(): String {
        delay(1000)

        if (secondsCounter + 1 > 60) {
            secondsCounter = 0
        } else {
            secondsCounter++
        }

        return String.format("%d:%d:%d", hoursCounter, minutesCounter, secondsCounter)
    }
}

