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
import com.resucito.app.presentation.ui.navigation.Album
import com.resucito.app.presentation.ui.navigation.Home
import com.resucito.app.presentation.ui.navigation.More
import com.resucito.app.presentation.ui.navigation.Search
import com.resucito.app.presentation.ui.navigation.Song
import com.resucito.app.presentation.ui.navigation.Start
import com.resucito.app.presentation.ui.screen.album.AlbumScreen
import com.resucito.app.presentation.ui.screen.home.HomeScreen
import com.resucito.app.presentation.ui.screen.more.MoreScreen
import com.resucito.app.presentation.ui.screen.search.SearchScreen
import com.resucito.app.presentation.ui.screen.song.SongScreen
import com.resucito.app.presentation.ui.screen.start.StartScreen
import com.resucito.app.presentation.ui.theme.ResucitoTheme
import com.resucito.app.presentation.viewmodel.AlbumScreenViewModel
import com.resucito.app.presentation.viewmodel.ApplicationViewModel
import com.resucito.app.presentation.viewmodel.HomeScreenViewModel
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel
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
                    onToggleTheme = { appViewModel.setIsDarkMode(it)})
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
            NavHost(navController, startDestination = if (isFirstRun) Start else Home) {
                composable<Start> {
                    val viewModel: StartScreenViewModel = hiltViewModel()
                    StartScreen(
                        navController = navController,
                        onCreate = viewModel::onCreate,
                        isLoading = viewModel.isLoading,
                        isError = viewModel.isError,
                        onToggleFirstRun = onToggleFirstRun
                    )
                }
                composable<Home> {
                    val viewModel: HomeScreenViewModel = hiltViewModel()
                    HomeScreen(
                        navController,
                        isDarkTheme,
                        onToggleTheme,
                        viewModel::getCountStage,
                        viewModel::getCountCategory
                    )
                }
                composable<Search> { navBackStackEntry ->
                    val (stageId, categoryId) = navBackStackEntry.toRoute<Search>()

                    val viewModel: SearchScreenViewModel = hiltViewModel()

                    SearchScreen(
                        navController = navController,
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
                composable<Song> { backStackEntry ->
                    val songRoute: Song = backStackEntry.toRoute()

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
                composable<Album> {
                    val viewModel: AlbumScreenViewModel = hiltViewModel()
                    AlbumScreen(
                        getAlbums = viewModel::getAllAlbums,
                        isLoading = viewModel.isLoading,
                        isError = viewModel.isError,
                        favoriteSongs = viewModel.favoriteSongs
                    )
                }
                composable<More> {
                    MoreScreen()
                }
            }
        }
    }
}

