package com.jop.marketjp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.min

@Composable
fun CustomLoading(
    isLoading: Boolean = false
){
    if (isLoading){
        val colorPrimary = MaterialTheme.colorScheme.primary

        val infiniteTransition = rememberInfiniteTransition(label = "")
        val progress by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )
        Dialog(onDismissRequest = {  }){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radius = min(size.width, size.height) / 3

                    // Coordenadas de los vértices del triángulo
                    val point1 = Offset(centerX, centerY - radius)
                    val point2 = Offset(centerX - radius * 0.87f, centerY + radius / 2)
                    val point3 = Offset(centerX + radius * 0.87f, centerY + radius / 2)

                    // Lista de puntos del triángulo
                    val points = listOf(point1, point2, point3, point1)

                    // Longitud total del perímetro del triángulo
                    val totalLength = listOf(
                        points[0].distanceTo(points[1]),
                        points[1].distanceTo(points[2]),
                        points[2].distanceTo(points[3])
                    ).sum()

                    // Longitud parcial para cada línea animada
                    val segmentLength = totalLength / 3

                    for (i in 0..2) {
                        val startOffset = (progress * totalLength + i * segmentLength) % totalLength
                        val endOffset = startOffset + segmentLength

                        var currentOffset = startOffset
                        var currentPointIndex = 0

                        // Encuentra el segmento inicial
                        while (currentOffset >= points[currentPointIndex].distanceTo(points[currentPointIndex + 1])) {
                            currentOffset -= points[currentPointIndex].distanceTo(points[currentPointIndex + 1])
                            currentPointIndex++
                        }

                        val start = Offset(
                            x = points[currentPointIndex].x + (points[currentPointIndex + 1].x - points[currentPointIndex].x) * (currentOffset / points[currentPointIndex].distanceTo(points[currentPointIndex + 1])),
                            y = points[currentPointIndex].y + (points[currentPointIndex + 1].y - points[currentPointIndex].y) * (currentOffset / points[currentPointIndex].distanceTo(points[currentPointIndex + 1]))
                        )

                        // Encuentra el segmento final
                        currentOffset = endOffset
                        currentPointIndex = 0
                        while (currentOffset >= points[currentPointIndex].distanceTo(points[(currentPointIndex + 1) % points.size])) {
                            currentOffset -= points[currentPointIndex].distanceTo(points[(currentPointIndex + 1) % points.size])
                            currentPointIndex = (currentPointIndex + 1) % points.size
                        }

                        val end = Offset(
                            x = points[currentPointIndex].x + (points[currentPointIndex + 1].x - points[currentPointIndex].x) * (currentOffset / points[currentPointIndex].distanceTo(points[currentPointIndex + 1])),
                            y = points[currentPointIndex].y + (points[currentPointIndex + 1].y - points[currentPointIndex].y) * (currentOffset / points[currentPointIndex].distanceTo(points[currentPointIndex + 1]))
                        )

                        drawLine(
                            color = colorPrimary,
                            start = start,
                            end = end,
                            strokeWidth = 8f,
                            cap = Stroke.DefaultCap
                        )
                    }
                }
            }
        }
    }

}

private fun Offset.distanceTo(other: Offset): Float {
    return kotlin.math.sqrt(
        (other.x - this.x) * (other.x - this.x) + (other.y - this.y) * (other.y - this.y)
    )
}