package com.plum.superheroapp.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme


private val LightColorScheme = lightColorScheme()

@Composable
fun SuperheroappTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
