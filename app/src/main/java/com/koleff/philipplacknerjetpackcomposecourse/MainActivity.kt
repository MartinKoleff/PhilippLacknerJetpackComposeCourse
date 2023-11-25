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
    private suspend fun startClock(): String {
        delay(1000)
        return dateTimeFormatter.format(LocalDateTime.now()) //To format as hh:mm:ss
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentTime by remember {
                mutableStateOf(dateTimeFormatter.format(LocalDateTime.now()))
            }

            LaunchedEffect(key1 = currentTime){
                currentTime = startClock()
            }

            Clock(modifier = Modifier.fillMaxSize(), currentTime = currentTime)
        }
    }
}

