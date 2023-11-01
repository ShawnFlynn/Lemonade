package com.example.lemonade.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Title,    // Main color = Lemon Yellow
    onPrimary = Text,   // text color for primary = Black
    primaryVariant = Dirty, // Darker primary = Dirty Yellow
    secondary = Teal,       // Secondary surfaces
    onSecondary = Text,
    background = White,     // App background
    onBackground = Text     // Text for background

    /* Other default colors to override
    surface = Color.White,
    onSurface = Color.Black,
    */
)

@Composable
fun LemonadeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}