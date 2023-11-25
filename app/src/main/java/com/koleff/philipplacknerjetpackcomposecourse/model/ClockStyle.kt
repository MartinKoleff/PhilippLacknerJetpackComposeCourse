package com.koleff.philipplacknerjetpackcomposecourse.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
    val clockRadius: Dp = 150.dp,
    val hourArrowColor: Color = Color.Red,
    val normalArrowColor: Color = Color.Black,
    val arrowSize: Dp = 50.dp
)