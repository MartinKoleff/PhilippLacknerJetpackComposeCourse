package com.koleff.philipplacknerjetpackcomposecourse.model

sealed class LineType{
    object Hour : LineType()
    object Minute : LineType()
}
