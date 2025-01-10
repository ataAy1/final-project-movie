package com.finalproject.movie.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun CinemaxOverlay(
    color1: Color,
    color2: Color,
    alpha: Float,
    modifier: Modifier = Modifier,
    brush: Brush = Brush.verticalGradient(
        listOf(color1.copy(alpha = alpha), color2.copy(alpha = alpha))
    )
) {
    Box(
        modifier = modifier
            .background(brush = brush)
            .fillMaxSize()
    )
}
