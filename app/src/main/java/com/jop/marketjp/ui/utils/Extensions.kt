package com.jop.marketjp.ui.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random


fun getRandomNumber(max: Int): Int {
    return Random.nextInt(0, max) // Genera un número entre 0 y max (inclusive)
}
fun List<String>.cleanUrls(): List<String> {
    return this.map { url ->
        // Usar expresión regular para eliminar caracteres no deseados
        url.replace(Regex("[^a-zA-Z0-9:/?&=._-]"), "")
    }
}

fun String.isValidDouble(): Boolean = this.isEmpty() || this.matches(Regex("^\\d*\\.?\\d*\$"))
fun String.isValidInt(): Boolean = this.isEmpty() || this.toIntOrNull() != null


fun dynamicTextColor(colors: List<Color>?): Color {
    if (colors == null || colors.size < 2) return Color.Black
    return try {
        val averageColor = Color(
            red = (colors[0].red + colors[1].red) / 2,
            green = (colors[0].green + colors[1].green) / 2,
            blue = (colors[0].blue + colors[1].blue) / 2,
            alpha = 1f
        )
        val luminance = (averageColor.red * 0.299 + averageColor.green * 0.587 + averageColor.blue * 0.114)
        if (luminance < 0.5) Color.White else Color.Black
    }catch (e: Exception){
        Color.Black
    }
}
