package com.resucito.app.presentation.ui.components

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.resucito.app.presentation.ui.navigation.Routes

@Composable
fun NavigationStatusBar(navController: NavHostController, isDarkTheme: Boolean) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val statusBarDefaultColor = MaterialTheme.colorScheme.background.toArgb()
    val statusBarSecondaryColor = MaterialTheme.colorScheme.surfaceContainer.toArgb()

    var statusColor by remember {
        mutableIntStateOf(statusBarDefaultColor)
    }

    val navStatusBarColor = listOf(Routes.Library, Routes.SongBook, Routes.More)

    val navDestination = navBackStackEntry?.destination

    statusColor = if (navDestination == null) statusBarDefaultColor
    else if (navStatusBarColor.any { navController.graph[it] == navDestination }) {
        statusBarSecondaryColor
    } else statusBarDefaultColor


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !isDarkTheme
        }
    }

}