package com.koleff.philipplacknerjetpackcomposecourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import com.koleff.philipplacknerjetpackcomposecourse.composable.MyCanvas
import com.koleff.philipplacknerjetpackcomposecourse.ui.theme.PhilippLacknerJetpackComposeCourseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCanvas()

            //if only purpose is to draw on this component -> use Canvas
            //if l want to draw stuff behind composable -> use .drawBehind{}
            Box(modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    //Same as canvas DrawScope...
                })
        }
    }
}

