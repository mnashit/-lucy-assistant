package com.mnashit.lucy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LucyColorScheme = darkColorScheme(
    primary = Color(0xFF168BFF),
    onPrimary = Color.White,

    secondary = Color(0xFF00E5FF),
    onSecondary = Color(0xFF080D1A),

    background = Color(0xFF080D1A),
    onBackground = Color.White,

    surface = Color(0xFF111D32),
    onSurface = Color.White,

    surfaceVariant = Color(0xFF172944),
    onSurfaceVariant = Color(0xFFB0C4D8),

    error = Color(0xFFFF5252),
    onError = Color.White
)

@Composable
fun LucyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LucyColorScheme,
        content = content
    )
}
