package com.koleff.philipplacknerjetpackcomposecourse.utils

import kotlin.math.PI

object DegreeUtils {

    fun toRadian(degree: Float): Float{
        return degree * (PI / 180f).toFloat()
    }

    fun toDegree(radian: Float): Float{
        return radian * (180f / PI).toFloat()
    }
}