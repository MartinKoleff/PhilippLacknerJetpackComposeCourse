package com.koleff.philipplacknerjetpackcomposecourse.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
    val clockRadius: Dp = 150.dp,
    val hoursArrowColor: Color = Color.LightGray,
    val minutesArrowColor: Color = Color.Black,
    val secondsArrowColor: Color = Color.Red,
    val arrowLength: Dp = 50.dp,
    val hourLineLength: Dp = 35.dp,
    val minuteLineLength: Dp = 30.dp,
    val minutesLineColor: Color = Color.Black,
    val hoursLineColor: Color = Color.Green,
    val totalHours: Int = 12,
    val minuteLinesPerHour: Int = 5
)