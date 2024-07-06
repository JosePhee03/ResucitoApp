package com.resucito.app.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.resucito.app.presentation.ui.components.NavigationBottomBar
import com.resucito.app.presentation.ui.components.NavigationStatusBar
import com.resucito.app.presentation.ui.navigation.Routes
import com.resucito.app.presentation.ui.screen.home.HomeScreen
import com.resucito.app.presentation.ui.screen.library.LibraryScreen
import com.resucito.app.presentation.ui.screen.more.MoreScreen
import com.resucito.app.presentation.ui.screen.search.SearchScreen
import com.resucito.app.presentation.ui.screen.song.SongScreen
import com.resucito.app.presentation.ui.screen.songbook.SongBookScreen
import com.resucito.app.presentation.ui.screen.start.StartScreen
import com.resucito.app.presentation.ui.theme.ResucitoTheme
import com.resucito.app.presentation.viewmodel.ApplicationViewModel
import com.resucito.app.presentation.viewmodel.HomeScreenViewModel
import com.resucito.app.presentation.viewmodel.LibraryScreenViewModel
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel
import com.resucito.app.presentation.viewmodel.SongBookScreenViewModel
import com.resucito.app.presentation.viewmodel.SongScreenViewModel
import com.resucito.app.presentation.viewmodel.StartScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appViewModel: ApplicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { false }
        setContent {

            appViewModel.onCreate(isSystemInDarkTheme())

            ResucitoTheme(appViewModel.isDarkTheme) {
                MainScreen(
                    isFirstRun = appViewModel.getIsFirstRun(),
                    onToggleFirstRun = { appViewModel.setIsFirstRun(it) },
                    isDarkTheme = appViewModel.isDarkTheme,
                    onToggleTheme = { appViewModel.setIsDarkMode(it) })
            }
        }
    }

}

@Composable
fun MainScreen(
    isFirstRun: Boolean,
    onToggleFirstRun: (Boolean) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {

    val navController = rememberNavController()
    val snackBarController = remember { SnackbarHostState() }

    NavigationStatusBar(
        navController,
        isDarkTheme
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarController)
        },
        bottomBar = { NavigationBottomBar(navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavHost(
                navController,
                startDestination = if (isFirstRun) Routes.Start else Routes.Home
            ) {
                composable<Routes.Start> {
                    val viewModel: StartScreenViewModel = hiltViewModel()
                    StartScreen(
                        navigateToHome = { navController.navigate(Routes.Home) },
                        onRemoveStack = { navController.popBackStack() },
                        onCreate = viewModel::onCreate,
                        isLoading = viewModel.isLoading,
                        isError = viewModel.isError,
                        onToggleFirstRun = onToggleFirstRun
                    )
                }
                composable<Routes.Home> {
                    val viewModel: HomeScreenViewModel = hiltViewModel()
                    HomeScreen(
                        navigateToSearch = { stageId, categoryId ->
                            navController.navigate(
                                Routes.Search(
                                    stageId,
                                    categoryId
                                )
                            )
                        },
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = onToggleTheme,
                        getCountStage = viewModel::getCountStage,
                        getCountCategory = viewModel::getCountCategory
                    )
                }
                composable<Routes.Search> { navBackStackEntry ->
                    val (stageId, categoryId) = navBackStackEntry.toRoute<Routes.Search>()

                    val viewModel: SearchScreenViewModel = hiltViewModel()

                    SearchScreen(
                        navigateToSong = { navController.navigate(Routes.Song(it)) },
                        stageId = stageId,
                        categoryId = categoryId,
                        songs = viewModel.songs,
                        filters = viewModel.filters,
                        isLoading = viewModel.isLoading,
                        searchSong = viewModel::searchSong,
                        setSearchFilter = viewModel::setSearchFilters,
                        switchFavoriteSong = viewModel::switchFavoriteSong,
                        snackBarText = viewModel.snackBarText,
                        snackBarController = snackBarController
                    )
                }
                composable<Routes.Song> { backStackEntry ->
                    val songRoute: Routes.Song = backStackEntry.toRoute()

                    val viewModel: SongScreenViewModel = hiltViewModel()

                    SongScreen(
                        navController = navController,
                        snackBarController = snackBarController,
                        song = viewModel.song,
                        isLoading = viewModel.isLoading,
                        isError = viewModel.isError,
                        findSong = viewModel::findSongById,
                        songId = songRoute.id,
                        onChangeFavorite = viewModel::switchFavoriteSong,
                        snackBarText = viewModel.snackBarText
                    )
                }
                composable<Routes.Library> {
                    val viewModel: LibraryScreenViewModel = hiltViewModel()
                    LibraryScreen(
                        navigateToSongBook = { navController.navigate(Routes.SongBook) },
                        getAlbums = viewModel::getAllFavoriteSongs,
                        isLoading = viewModel.isLoading,
                        isError = viewModel.isError,
                        favoriteSongs = viewModel.favoriteSongs
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
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = onToggleTheme
                    )
                }
            }
        }
    }
}

