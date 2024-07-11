package com.resucito.app.presentation.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.resucito.app.presentation.ui.screen.home.HomeScreen
import com.resucito.app.presentation.ui.screen.library.LibraryScreen
import com.resucito.app.presentation.ui.screen.more.MoreScreen
import com.resucito.app.presentation.ui.screen.search.SearchScreen
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
            StartScreen(vm = startScreenViewModel,
                navigateToHome = { navController.navigate(Routes.Home) },
                onRemoveStack = { navController.popBackStack() },
                onToggleFirstRun = onToggleFirstRun
            )
        }
        composable<Routes.Home> {
            HomeScreen(
                vm = homeScreenViewModel,
                navigateToSearch = { stageId, categoryId ->
                    navController.navigate(
                        Routes.Search(
                            stageId, categoryId
                        )
                    )
                },
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )
        }
        composable<Routes.Search> { navBackStackEntry ->
            val (stageId, categoryId) = navBackStackEntry.toRoute<Routes.Search>()

            SearchScreen(
                navigateToSong = { navController.navigate(Routes.Song(it)) },
                stageId = stageId,
                categoryId = categoryId,
                songs = searchScreenViewModel.songs,
                filters = searchScreenViewModel.filters,
                isLoading = searchScreenViewModel.isLoading,
                searchSong = searchScreenViewModel::searchSong,
                setSearchFilter = searchScreenViewModel::setSearchFilters,
                switchFavoriteSong = searchScreenViewModel::switchFavoriteSong,
                snackBarText = searchScreenViewModel.snackBarText,
                snackBarController = snackBarController
            )
        }
        composable<Routes.Song> { backStackEntry ->
            val songRoute: Routes.Song = backStackEntry.toRoute()

            SongScreen(
                navController = navController,
                snackBarController = snackBarController,
                song = songScreenViewModel.song,
                isLoading = songScreenViewModel.isLoading,
                isError = songScreenViewModel.isError,
                findSong = songScreenViewModel::findSongById,
                songId = songRoute.id,
                onChangeFavorite = songScreenViewModel::switchFavoriteSong,
                snackBarText = songScreenViewModel.snackBarText
            )
        }
        composable<Routes.Library> {
            LibraryScreen(
                navigateToSongBook = { navController.navigate(Routes.SongBook) },
                getAlbums = libraryScreenViewModel::getAllFavoriteSongs,
                isLoading = libraryScreenViewModel.isLoading,
                isError = libraryScreenViewModel.isError,
                favoriteSongs = libraryScreenViewModel.favoriteSongs
            )
        }
        composable<Routes.SongBook> {
            val viewModel: SongBookScreenViewModel = hiltViewModel()
            SongBookScreen(
                uiState = viewModel.uiState,
                onBackNavigate = { navController.navigateUp() },
                getSongBook = viewModel::getAllFavoriteSongs,
                navigateToSong = { songId -> navController.navigate(Routes.Song(songId)) },
                onChangeFavorite = viewModel::switchFavoriteSong
            )
        }
        composable<Routes.More> {
            MoreScreen(
                isDarkTheme = isDarkTheme, onToggleTheme = onToggleTheme
            )
        }
    }
}