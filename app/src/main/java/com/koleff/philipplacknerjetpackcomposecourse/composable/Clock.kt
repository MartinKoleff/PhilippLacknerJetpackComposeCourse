package com.koleff.philipplacknerjetpackcomposecourse.composable

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.koleff.philipplacknerjetpackcomposecourse.model.ClockStyle
import com.koleff.philipplacknerjetpackcomposecourse.model.LineType
import com.koleff.philipplacknerjetpackcomposecourse.utils.DegreeUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Clock(
    modifier: Modifier,
    currentTime: String = dateTimeFormatter.format(LocalDateTime.now()),
    clockStyle: ClockStyle = ClockStyle()
) {
    var time by remember {
        mutableStateOf(currentTime)
    }

    var hours: Float by remember {
        mutableStateOf(time.split(":")[0].toFloat())
    }

    var minutes: Float by remember {
        mutableStateOf(time.split(":")[1].toFloat())
    }

    var seconds: Float by remember {
        mutableStateOf(time.split(":")[2].toFloat())
    }

    //When currentTime updates -> update time...
    LaunchedEffect(key1 = currentTime) {
        time = currentTime
        hours = time.split(":")[0].toFloat()
        minutes = time.split(":")[1].toFloat()
        seconds = time.split(":")[2].toFloat()
    }

    Canvas(modifier = modifier) {

        //Draw circle
        drawContext.canvas.nativeCanvas.apply {
            val circleCenter = center
            val outerRadius = clockStyle.clockRadius.toPx()

            drawCircle(
                center.x,
                center.y,
                clockStyle.clockRadius.toPx(),
                Paint().apply {
                    strokeWidth = 5.dp.toPx()
                    color = Color.WHITE
                    style = Paint.Style.FILL_AND_STROKE
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0) //Black
                    )
                }
            )

            //Timer display
            drawText(
                time,
                center.x,
                center.y + 200.dp.toPx(),
                Paint().apply {
                    color = Color.RED
                    textSize = 20.sp.toPx()
                    textAlign = Paint.Align.CENTER
                }
            )

            //TODO: Clock lines for hours and minutes (5 for minutes and 1 hour) | Angle -> 360 / 12 (hours) | minutes -> 360 / 48 | Overall 360 / 60 ?
            for (i in 0..clockStyle.minuteLinesPerHour * clockStyle.totalHours) {
                val lineType: LineType = when {
                    i % 5 == 0 -> LineType.Hour
                    else -> LineType.Minute
                }

                val lineLength = when (lineType) {
                    LineType.Minute -> {
                        clockStyle.minuteLineLength.toPx()
                    }

                    LineType.Hour -> {
                        clockStyle.hourLineLength.toPx()
                    }
                }

                val lineColor = when (lineType) {
                    LineType.Minute -> clockStyle.minutesLineColor
                    LineType.Hour -> clockStyle.hoursLineColor
                }

                val angleInRadian = DegreeUtils.toRadian((i * 6).toFloat())

                val lineStart = Offset(
                    x = (outerRadius - lineLength) * cos(angleInRadian) + circleCenter.x,
                    y = (outerRadius - lineLength) * sin(angleInRadian) + circleCenter.y
                )

                val lineEnd = Offset(
                    x = outerRadius * cos(angleInRadian) + circleCenter.x,
                    y = outerRadius * sin(angleInRadian) + circleCenter.y
                )


                //Drawing clock time lines
                drawLine(
                    color = lineColor,
                    start = lineStart,
                    end = lineEnd,
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Seconds arrow
            rotate(degrees = seconds * (360f / 60f)) {
                drawLine(
                    color = clockStyle.secondsArrowColor,
                    start = center,
                    end = Offset(center.x, center.y + clockStyle.arrowLength.toPx()),
                    strokeWidth = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Minutes arrow
            rotate(degrees = minutes * (360f / 60f)) {
                drawLine(
                    color = clockStyle.minutesArrowColor,
                    start = center,
                    end = Offset(center.x,center.y + clockStyle.arrowLength.toPx()),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            // Hours arrow
            rotate(degrees = hours * (360f / 12f)) {
                drawLine(
                    color = clockStyle.hoursArrowColor,
                    start = center,
                    end = Offset(center.x, center.y + clockStyle.arrowLength.toPx()),
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}