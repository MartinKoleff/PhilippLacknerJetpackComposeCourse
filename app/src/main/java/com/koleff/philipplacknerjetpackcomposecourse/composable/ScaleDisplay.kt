package com.koleff.philipplacknerjetpackcomposecourse.composable

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun ScaleDisplay(
    modifier: Modifier = Modifier,
    displayTextSize: TextUnit = 50.sp,
    displayTextColor: Int = Color.RED,
    currentWeight: Int
) {
    var weight by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(currentWeight) {
        weight = currentWeight
    }

    //    DisposableEffect(key1 = weight){
    //        onDispose {
    //
    //        }
    //    }

    Canvas(modifier = modifier) {
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                weight.toString(),
                center.x,
                center.y,
                Paint().apply {
                    textSize = displayTextSize.toPx()
                    textAlign = Paint.Align.CENTER
                    color = displayTextColor
                }
            )
        }
    }
}