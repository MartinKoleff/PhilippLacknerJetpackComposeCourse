package com.koleff.philipplacknerjetpackcomposecourse

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)) {
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "Test Text",
                        100f,
                        100f,
                        Paint().apply {
                            color = Color.RED
                            textSize = 100f
                        }
                    )
                }
            }
        }
    }
}

