package com.github.networkguild.utils

import java.awt.Color
import kotlin.random.Random

object Colors {

    val randomColor: Color
        get() {
            val red = Random.nextInt(256)
            val green = Random.nextInt(256)
            val blue = Random.nextInt(256)
            return Color(red, green, blue)
        }
}
