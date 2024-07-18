package com.resucito.app.presentation.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
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
import com.resucito.app.presentation.viewmodel.SongScreenViewModel
import com.resucito.app.presentation.viewmodel.StartScreenViewModel

@Composable
fun NavigationScreen(
    navController: NavHostController,
    snackBarController: SnackbarHostState,
    applicationViewModel: ApplicationViewModel,
    applicationState: ApplicationState,
    startScreenViewModel: StartScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    searchScreenViewModel: SearchScreenViewModel,
    songScreenViewModel: SongScreenViewModel,
    songBookScreenViewModel: SongBookScreenViewModel,
    libraryScreenViewModel: LibraryScreenViewModel
) {

    val isFirstRun = applicationState.isFirstRun
    val isDarkTheme = applicationState.isDarkTheme
    val onToggleFirstRun = remember<(Boolean) -> Unit> {
        { applicationViewModel.setIsFirstRun(it) }
    }
    val onToggleTheme = remember<(Boolean) -> Unit> {
        { applicationViewModel.setIsDarkMode(it) }
    }

    NavHost(
        navController, startDestination = if (isFirstRun) Routes.Start else Routes.Home
    ) {
        composable<Routes.Start> {
            StartScreen(
                vm = startScreenViewModel,
                navigateToHome = { navController.navigate(Routes.Home) },
                onRemoveStack = { navController.popBackStack() },
                onToggleFirstRun = onToggleFirstRun
            )
        }
        composable<Routes.Home> {
            MainScreen(
                initialPage = 0,
                homeScreenViewModel = homeScreenViewModel,
                searchScreenViewModel = searchScreenViewModel,
                libraryScreenViewModel = libraryScreenViewModel,
                navController = navController,
                onToggleTheme = onToggleTheme,
                isDarkTheme = isDarkTheme
            )
        }
        composable<Routes.Search> { navBackStackEntry ->
            val (stageId, categoryId) = navBackStackEntry.toRoute<Routes.Search>()

            MainScreen(
                initialPage = 1,
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

            SongScreen(
                vm = songScreenViewModel,
                snackBarController = snackBarController,
                songId = songRoute.id,
                navigateToSearch = { stageId, categoryId ->
                    navController.navigate(
                        Routes.Search(
                            stageId, categoryId
                        )
                    )
                },
                onBackNavigate = { navController.navigateUp() }
            )
        }
        composable<Routes.SongBook> {
            SongBookScreen(
                vm = songBookScreenViewModel,
                onBackNavigate = { navController.navigateUp() },
                navigateToSong = { songId -> navController.navigate(Routes.Song(songId)) },
            )
        }
    }
}