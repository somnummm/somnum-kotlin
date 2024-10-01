package com.example.somnum.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF9381FF),
    onPrimary = Color.White,
    secondary = Color(0xFFB8B8FF),
    onSecondary = Color.White,
    surface = Color(0xFFF8F7FF),
    onSurface = Color(0xFF9381FF),
    background = Color(0xFFF8F7FF),
    onBackground = Color(0xFF9381FF)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9381FF),
    onPrimary = Color.Black,
    secondary = Color(0xFFB8B8FF),
    onSecondary = Color.Black,
    surface = Color(0xFF1F1F1F),
    onSurface = Color(0xFFF8F7FF),
    background = Color(0xFF1F1F1F),
    onBackground = Color(0xFFF8F7FF)
)

@Composable
fun SomnumTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
