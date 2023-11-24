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
import androidx.compose.ui.unit.dp
import com.koleff.philipplacknerjetpackcomposecourse.model.LineType
import com.koleff.philipplacknerjetpackcomposecourse.model.ScaleStyle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

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

    var angle by remember {
        mutableStateOf(0f)
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

        //Drawing weight lines
        for(i in minWeight..maxWeight){
            val angleInRad = (i - initialWeight + angle - 90) * (PI / 180f).toFloat() //Polar coordinates

            val lineType = when {
                i % 10 == 0 -> LineType.TenStepLine
                i % 5 == 0 -> LineType.FiveStepLine
                else -> LineType.NormalLine
            }

            val lineLength = when (lineType){
                LineType.NormalLine -> scaleStyle.normalLineLength.toPx()
                LineType.FiveStepLine -> scaleStyle.fiveStepLineLength.toPx()
                LineType.TenStepLine -> scaleStyle.tenStepLineLength.toPx()
            }

            val lineColor = when (lineType){
                LineType.NormalLine -> scaleStyle.normalLineColor
                LineType.FiveStepLine -> scaleStyle.fiveStepLineColor
                LineType.TenStepLine -> scaleStyle.tenStepLineColor
            }

            val lineStart = Offset(
                x = (outerRadius - lineLength) * cos(angleInRad) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRad) + circleCenter.y
            )

            val lineEnd = Offset(
                x = outerRadius * cos(angleInRad) + circleCenter.x,
                y = outerRadius * sin(angleInRad) + circleCenter.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
        }
    }
}