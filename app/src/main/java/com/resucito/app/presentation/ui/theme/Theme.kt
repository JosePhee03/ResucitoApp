package com.resucito.app.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LightColorPalette = lightColorScheme(
    primary = LightModeColors.primary,
    onPrimary = LightModeColors.onPrimary,
    primaryContainer = LightModeColors.primaryContainer,
    onPrimaryContainer = LightModeColors.onPrimaryContainer,
    secondary = LightModeColors.secondary,
    onSecondary = LightModeColors.onSecondary,
    secondaryContainer = LightModeColors.secondaryContainer,
    onSecondaryContainer = LightModeColors.onSecondaryContainer,
    tertiary = LightModeColors.tertiary,
    onTertiary = LightModeColors.onTertiary,
    tertiaryContainer = LightModeColors.tertiaryContainer,
    onTertiaryContainer = LightModeColors.onTertiaryContainer,
    error = LightModeColors.error,
    onError = LightModeColors.onError,
    errorContainer = LightModeColors.errorContainer,
    onErrorContainer = LightModeColors.onErrorContainer,
    background = LightModeColors.background,
    onBackground = LightModeColors.onBackground,
    surface = LightModeColors.surface,
    onSurface = LightModeColors.onSurface,
    surfaceVariant = LightModeColors.surfaceVariant,
    onSurfaceVariant = LightModeColors.onSurfaceVariant,
    outline = LightModeColors.outline,
    outlineVariant = LightModeColors.outlineVariant
)

val DarkColorPalette = darkColorScheme(
    primary = DarkModeColors.primary,
    onPrimary = DarkModeColors.onPrimary,
    primaryContainer = DarkModeColors.primaryContainer,
    onPrimaryContainer = DarkModeColors.onPrimaryContainer,
    secondary = DarkModeColors.secondary,
    onSecondary = DarkModeColors.onSecondary,
    secondaryContainer = DarkModeColors.secondaryContainer,
    onSecondaryContainer = DarkModeColors.onSecondaryContainer,
    tertiary = DarkModeColors.tertiary,
    onTertiary = DarkModeColors.onTertiary,
    tertiaryContainer = DarkModeColors.tertiaryContainer,
    onTertiaryContainer = DarkModeColors.onTertiaryContainer,
    error = DarkModeColors.error,
    onError = DarkModeColors.onError,
    errorContainer = DarkModeColors.errorContainer,
    onErrorContainer = DarkModeColors.onErrorContainer,
    background = DarkModeColors.background,
    onBackground = DarkModeColors.onBackground,
    surface = DarkModeColors.surface,
    onSurface = DarkModeColors.onSurface,
    surfaceVariant = DarkModeColors.surfaceVariant,
    onSurfaceVariant = DarkModeColors.onSurfaceVariant,
    outline = DarkModeColors.outline,
    outlineVariant = DarkModeColors.outlineVariant
)

@Composable
fun ResucitoTheme(
    isDarkTheme: Boolean,
    content: @Composable() () -> Unit
) {

    val colorScheme = if (isDarkTheme) DarkColorPalette else LightColorPalette

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = Shapes(),
        content = content
    )
}