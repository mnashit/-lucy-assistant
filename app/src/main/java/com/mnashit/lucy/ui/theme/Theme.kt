package com.mnashit.lucy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LucyColorScheme = darkColorScheme(
    primary = Color(0xFF2196F3),
    secondary = Color(0xFF00BCD4),
    background = Color(0xFF080D1A),
    surface = Color(0xFF101A2C),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun LucyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LucyColorScheme,
        content = content
    )
}
