package com.koleff.philipplacknerjetpackcomposecourse.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random


@Composable
fun CirclePicker(
    radius: Float = 100f,
    enabled: Boolean = false,
    circleColor: Color = Color.Red,
    onCircleClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var circlePosition by remember {
            mutableStateOf(
                randomOffset(
                    radius = radius,
                    screenWidth = constraints.maxWidth,
                    screenHeight = constraints.maxHeight
                )
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(enabled) {
                    //Don't check for input if not started the game...
                    if (!enabled) {
                        return@pointerInput
                    }

                    //Touch listener...
                    detectTapGestures {
                        val distance = sqrt(
                            (it.x - circlePosition.x).pow(2) +
                                    (it.y - circlePosition.y).pow(2)
                        )
                        if (distance <= radius) {

                            //Generate new position...
                            circlePosition = randomOffset(
                                radius = radius,
                                screenWidth = constraints.maxWidth,
                                screenHeight = constraints.maxHeight
                            )
                            onCircleClick.invoke()
                        }
                    }
                }
        ) {
            drawCircle(
                color = circleColor,
                radius = radius,
                center = circlePosition
            )
        }
    }
}

fun randomOffset(radius: Float, screenWidth: Int, screenHeight: Int): Offset {
    return Offset(
        x = Random.nextInt(radius.roundToInt(), screenWidth - radius.roundToInt()).toFloat(),
        y = Random.nextInt(radius.roundToInt(), screenHeight - radius.roundToInt()).toFloat()
    )
}