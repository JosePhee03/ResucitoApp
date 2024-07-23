package com.resucito.app.presentation.ui.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


@Composable
fun SetSystemBarsColors(statusBarColor: Int, navigationBarColor: Int, isDarkTheme: Boolean) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusBarColor
            window.navigationBarColor = navigationBarColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !isDarkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !isDarkTheme
        }
    }
}