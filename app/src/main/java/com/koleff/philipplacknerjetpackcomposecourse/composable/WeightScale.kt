package com.koleff.philipplacknerjetpackcomposecourse.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.koleff.philipplacknerjetpackcomposecourse.model.ScaleStyle

@Composable
fun WeightScale() {
    var scaleWeight by remember {
        mutableStateOf(80)
    }

    Column(modifier = Modifier.fillMaxSize()) {
//                Spacer(modifier = Modifier.weight(2f))

        //Weight display
        Box(
            modifier = Modifier
                .weight(2f)
        ) {
            ScaleDisplay(
                modifier = Modifier.fillMaxSize(),
                currentWeight = scaleWeight
            )
        }

        //Weight Scale
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Scale(
                modifier = Modifier
                    .fillMaxSize(),
                scaleStyle = ScaleStyle(scaleWidth = 150.dp, scaleIndicatorLength = 60.dp)
            ) {
                scaleWeight = it
            }
        }
    }
}
