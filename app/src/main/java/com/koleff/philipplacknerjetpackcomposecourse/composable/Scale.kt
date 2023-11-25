package com.koleff.philipplacknerjetpackcomposecourse.composable

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import com.koleff.philipplacknerjetpackcomposecourse.model.LineType
import com.koleff.philipplacknerjetpackcomposecourse.model.ScaleStyle
import com.koleff.philipplacknerjetpackcomposecourse.utils.DegreeUtils
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
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

    //Used for dragging
    var oldAngle by remember {
        mutableStateOf(angle)
    }

    //Used for dragging
    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }

    Canvas(modifier = modifier
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { offset ->
                    dragStartedAngle = DegreeUtils.toDegree(
                        -atan2(
                            circleCenter.x - offset.x,
                            circleCenter.y - offset.y
                        )
                    )
                },
                onDragEnd = {
                    oldAngle = angle
                }
            ) { change, _ ->
                val touchAngle = DegreeUtils.toDegree(
                    -atan2(
                        circleCenter.x - change.position.x,
                        circleCenter.y - change.position.y
                    )
                )

                val newAngle = oldAngle + (touchAngle - dragStartedAngle)

                //Limit the weight
                angle = newAngle.coerceIn(
                    minimumValue = initialWeight - maxWeight.toFloat(),
                    maximumValue = initialWeight - minWeight.toFloat()
                )
                onWeightChange((initialWeight - angle).roundToInt())
            }
        }
    ) {
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
        for (i in minWeight..maxWeight) {
            val angleInRad =
                DegreeUtils.toRadian(i - initialWeight + angle - 90) //Polar coordinates

            val lineType = when {
                i % 10 == 0 -> LineType.TenStepLine
                i % 5 == 0 -> LineType.FiveStepLine
                else -> LineType.NormalLine
            }

            val lineLength = when (lineType) {
                LineType.NormalLine -> scaleStyle.normalLineLength.toPx()
                LineType.FiveStepLine -> scaleStyle.fiveStepLineLength.toPx()
                LineType.TenStepLine -> scaleStyle.tenStepLineLength.toPx()
            }

            val lineColor = when (lineType) {
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

            //Drawing numbers and rotating them based on angle
            drawContext.canvas.nativeCanvas.apply {
                if (lineType == LineType.TenStepLine) {
                    val textRadius =
                        outerRadius - lineLength - 5.dp.toPx() - scaleStyle.textSize.toPx()
                    val x = textRadius * cos(angleInRad) + circleCenter.x
                    val y = textRadius * sin(angleInRad) + circleCenter.y

                    withRotation(
                        degrees = DegreeUtils.toRadian(angleInRad + 90f),
                        pivotX = x,
                        pivotY = y
                    ) {
                        drawText(
                            abs(i).toString(),
                            x,
                            y,
                            Paint().apply {
                                textSize = scaleStyle.textSize.toPx()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }

            //Drawing scale weight lines
            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )

            //Drawing scale indicator
            val middlePoint = Offset(
                x = circleCenter.x,
                y = circleCenter.y - innerRadius - scaleStyle.scaleIndicatorLength.toPx()
            )

            val bottomLeftPoint = Offset(
                x = circleCenter.x - 5f,
                y = circleCenter.y - innerRadius
            )

            val bottomRightPoint = Offset(
                x = circleCenter.x + 5f,
                y = circleCenter.y - innerRadius
            )

            val indicator = Path().apply {
                moveTo(middlePoint.x, middlePoint.y)
                lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
                lineTo(bottomRightPoint.x, bottomRightPoint.y)
                lineTo(middlePoint.x, middlePoint.y)
            }

            drawPath(
                path = indicator,
                color = scaleStyle.scaleIndicatorColor
            )
        }
    }
}