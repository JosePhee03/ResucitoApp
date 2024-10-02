package com.resucito.app.presentation.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.resucito.app.presentation.ui.components.SetSystemBarsColors
import com.resucito.app.presentation.ui.screen.main.MainScreen
import com.resucito.app.presentation.ui.screen.song.SongScreen
import com.resucito.app.presentation.ui.screen.songbook.SongBookScreen
import com.resucito.app.presentation.ui.screen.start.StartScreen
import com.resucito.app.presentation.viewmodel.ApplicationState
import com.resucito.app.presentation.viewmodel.ApplicationViewModel
import com.resucito.app.presentation.viewmodel.HomeScreenViewModel
import com.resucito.app.presentation.viewmodel.LibraryScreenViewModel
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel
import com.resucito.app.presentation.viewmodel.SongBookScreenViewModel
import com.resucito.app.presentation.viewmodel.StartScreenViewModel

@Composable
fun NavigationScreen(
    navController: NavHostController,
    applicationViewModel: ApplicationViewModel,
    applicationState: ApplicationState,
    startScreenViewModel: StartScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    searchScreenViewModel: SearchScreenViewModel,
    songBookScreenViewModel: SongBookScreenViewModel,
    libraryScreenViewModel: LibraryScreenViewModel
) {

    val isFirstRun = applicationState.isFirstRun
    val isDarkTheme = applicationState.isDarkTheme
    val onToggleFirstRun = applicationViewModel::setIsFirstRun
    val onToggleTheme = applicationViewModel::setIsDarkMode

    val defaultColor = MaterialTheme.colorScheme.background.toArgb()
    val secondaryColor = MaterialTheme.colorScheme.surfaceContainer.toArgb()

    var statusBarColor by remember {
        mutableIntStateOf(defaultColor)
    }

    SetSystemBarsColors(
        statusBarColor = statusBarColor,
        navigationBarColor = defaultColor,
        isDarkTheme = isDarkTheme
    )

    NavHost(
        navController,
        startDestination = if (isFirstRun) Routes.Start else Routes.Main(Page.HOME)
    ) {
        composable<Routes.Start> {

            statusBarColor = defaultColor

            StartScreen(
                vm = startScreenViewModel,
                navigateToHome = { navController.navigate(Routes.Main(Page.HOME)) },
                onRemoveStack = { navController.popBackStack() },
                onToggleFirstRun = onToggleFirstRun
            )
        }
        composable<Routes.Main> { backStackEntry ->
            val routesMain: Routes.Main = backStackEntry.toRoute()

            MainScreen(
                initialPage = routesMain.page,
                isSecondaryStatusColor = {
                    statusBarColor = if (it) secondaryColor else defaultColor
                },
                homeScreenViewModel = homeScreenViewModel,
                searchScreenViewModel = searchScreenViewModel,
                libraryScreenViewModel = libraryScreenViewModel,
                navController = navController,
                onToggleTheme = onToggleTheme,
                isDarkTheme = isDarkTheme
            )
        }
        composable<Routes.Song> { backStackEntry ->
            val songRoute: Routes.Song = backStackEntry.toRoute()

            statusBarColor = defaultColor

            SongScreen(
                songId = songRoute.id,
                navigateToSearch = { stageId, categoryId ->
                    searchScreenViewModel.setFiltersById(stageId, categoryId)
                    navController.navigate(
                        Routes.Main(Page.SEARCH)
                    )
                },
                onBackNavigate = { navController.navigateUp() })
        }
        composable<Routes.SongBook> {

            statusBarColor = secondaryColor

            SongBookScreen(
                vm = songBookScreenViewModel,
                onBackNavigate = { navController.navigateUp() },
                navigateToSong = { songId -> navController.navigate(Routes.Song(songId)) },
            )
        }
    }
}