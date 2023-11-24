package com.koleff.philipplacknerjetpackcomposecourse.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Overview:
 * ----------------------
 * Offset -> coordinates
 * Size -> width and height
 * -------------------------
 * f -> pixel values
 * dp.toPx() -> dp converted to pixel values
 * --------------------------
 * */
@Composable
fun MyCanvas() {
    Canvas(
        modifier = Modifier
            .padding(20.dp)
            .size(300.dp) //fillMaxSize()
    ) {
        drawRect(
            color = Color.Black,
            size = size,
        )

        drawRect(
            color = Color.Red,
            size = Size(width = 10.dp.toPx(), height = 10.dp.toPx()),
            topLeft = center //Screen center starts drawing from bottom left point...
        )

        drawRect(
            color = Color.Green,
            size = Size(width = 150f, height = 150f),
            topLeft = Offset(x = 100f, y = 100f),
            style = Stroke(width = 5f) //by default -> Fill
        )

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Blue, Color.White),
                center = Offset(200f, 600f), //same offset as center or else the coloring is lost...
                radius = 100f
            ),
            center = Offset(200f, 600f), //by default -> center
            radius = 100f
        )

        drawArc(
            color = Color.Yellow,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = true,
            topLeft = Offset(500f, 0f), //can set negative coordinates
            size = Size(200f, 200f)
        )

        drawArc(
            color = Color.Magenta,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(500f, 300f), //can set negative coordinates
            size = Size(150f, 150f)
        )

        //Can be used as a loading screen animation
        drawArc(
            color = Color.Green,
            startAngle = 0f,
            sweepAngle = 270f,
            topLeft = Offset(500f, 600f),
            size = Size(150f, 150f),
            useCenter = false, //needed!
            style = Stroke(5f)
        )

        drawArc(
            color = Color.Green,
            startAngle = 180f,
            sweepAngle = 270f,
            topLeft = Offset(525f, 625f),
            size = Size(100f, 100f),
            useCenter = false,
            style = Stroke(5f)
        )

        drawOval(
            color = Color.Cyan,
            topLeft = Offset(center.x, 150f),
            size = Size(100f, 150f)
        )

        drawLine(
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Red, Color.Blue),
                startX = center.x + 50f,
                endX = center.x + 150f
            ),
            start = Offset(center.x + 50f, center.y + 50f),
            end = Offset(center.x + 150f, center.y + 150f),
            strokeWidth = 5.dp.toPx()
        )
    }
}

@Preview
@Composable
fun previewMyCanvas() {
    MyCanvas()
}