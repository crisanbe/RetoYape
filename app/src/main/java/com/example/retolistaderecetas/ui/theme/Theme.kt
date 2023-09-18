package com.example.retolistaderecetas.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary =   BLUE900,
    primaryVariant = BLUE950,
    secondary = CYAN900,
    secondaryVariant = CYAN800,
    background = BLUEGREY900,
    surface = BLUEGREY800,
    error = RED800,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.White
)

private val LightColorPalette = lightColors(
    primary =   YELLOW_ORANGE500,
    primaryVariant = CORVETTE800,
    secondary = SUSHI500,
    secondaryVariant = TEAL700,
    background = CORVETTE50,
    surface = CORVETTE100,
    error = RED600,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black
)
@Composable
fun RecipeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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