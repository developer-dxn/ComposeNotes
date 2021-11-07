package com.dxn.composenotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    onPrimary = Color.Yellow,
    background = Color.Black,
    onBackground = Color.Yellow,
    surface = LightBlue,
    onSurface = Color.Black,
)

private val LightColorPalette = darkColors(
    primary = Color.White,
    onPrimary = Color.Blue,
    background = Color.White,
    onBackground = Color.Blue,
    surface = LightBlue,
    onSurface = Color.Black,
)

@Composable
fun ComposeNotesTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = if (isDarkTheme) DarkColorPalette else LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}