package com.koleff.philipplacknerjetpackcomposecourse.composable

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import com.koleff.philipplacknerjetpackcomposecourse.model.ScaleStyle

@Composable
fun Scale(
    modifier: Modifier = Modifier,
    scaleStyle: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 80, //Can control with outside state in future
    onWeightChange: (Int) -> Unit
) {
    val radius = scaleStyle.radius
    val scaleWidth = scaleStyle.scaleWidth

    var center by remember { //Screen center?
        mutableStateOf(Offset.Zero)
    }

    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    Canvas(modifier = modifier) {
        center = this.center
        circleCenter = Offset(
            center.x,
            scaleWidth.toPx() / 2f + radius.toPx()
        ) //Middle of scale border "radius" + circle radius

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2

        //Compose cant draw shadows... This is why we use nativeCanvas
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    style = Paint.Style.STROKE
                    setShadowLayer( //Shadow blur radius (like in CSS) -> how much the shadow will expand
                        60f,
                        0f, //If you want to move the shadow...
                        0f,
                        Color.argb(50, 0, 0, 0) //Black
                    )
                }
            )
        }
    }
}