package com.resucito.app.presentation.ui.components

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.resucito.app.presentation.ui.navigation.Routes

@Stable
internal data class ListRoutes(
    val routes: List<Routes> = listOf(Routes.Library, Routes.SongBook, Routes.More)
)

@Composable
fun NavigationStatusBar(navController: NavHostController, isDarkTheme: Boolean) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val defaultColor = MaterialTheme.colorScheme.background.toArgb()
    val secondaryColor = MaterialTheme.colorScheme.surfaceContainer.toArgb()

    var statusColor by remember {
        mutableIntStateOf(defaultColor)
    }

    val navStatusBarColor = ListRoutes().routes

    val navDestination = navBackStackEntry?.destination

    statusColor = if (navDestination == null) defaultColor
    else if (navStatusBarColor.any { navController.graph[it] == navDestination }) {
        secondaryColor
    } else defaultColor

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = statusColor
            window.navigationBarColor = defaultColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !isDarkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !isDarkTheme
        }
    }

}