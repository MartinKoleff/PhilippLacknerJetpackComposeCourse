package com.koleff.philipplacknerjetpackcomposecourse.model

sealed class LineType{
    object NormalLine : LineType()
    object FiveStepLine : LineType()
    object TenStepLine : LineType()
}
