package com.resucito.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.resucito.app.componets.NavigationBottomBar
import com.resucito.app.data.Category
import com.resucito.app.data.Stage
import com.resucito.app.db.AppDatabase
import com.resucito.app.navigation.Album
import com.resucito.app.navigation.Home
import com.resucito.app.navigation.Search
import com.resucito.app.navigation.Song
import com.resucito.app.navigation.Start
import com.resucito.app.screen.album.AlbumScreen
import com.resucito.app.screen.home.HomeScreen
import com.resucito.app.screen.search.SearchScreen
import com.resucito.app.screen.song.SongScreen
import com.resucito.app.screen.start.StartScreen
import com.resucito.app.ui.theme.ResucitoTheme
import com.resucito.app.viewmodel.AppPreferencesProvider
import com.resucito.app.viewmodel.SearchFilters
import com.resucito.app.viewmodel.SearchSongProvider
import com.resucito.app.viewmodel.SongProvider
import com.resucito.app.viewmodel.SongsProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { false }
        setContent {
            val appPreferencesProvider: AppPreferencesProvider =
                viewModel(factory = ViewModelProvider.AndroidViewModelFactory(application))
            val songDao = AppDatabase.getInstance(this).songDao()

            val themeSystem = appPreferencesProvider.getThemePreferences(isSystemInDarkTheme())

            var isDarkTheme by remember {
                mutableStateOf(themeSystem)
            }

            ResucitoTheme(isDarkTheme) {
                val songsProvider = SongsProvider(songDao)
                val songProvider = SongProvider(songDao)
                val searchSongProvider = SearchSongProvider(songDao)
                MainScreen(appPreferencesProvider, songsProvider, songProvider, searchSongProvider,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { newTheme ->
                        isDarkTheme = newTheme
                        appPreferencesProvider.updateThemePreferences(newTheme)
                    })
            }
        }
    }

}

@Composable
fun MainScreen(
    appPreferencesProvider: AppPreferencesProvider,
    songsProvider: SongsProvider,
    songProvider: SongProvider,
    searchSongProvider: SearchSongProvider,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {

    val navController = rememberNavController()


    Scaffold(
        bottomBar = { NavigationBottomBar(navController) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            NavHost(navController, startDestination = Start) {
                composable<Start> {
                    StartScreen(navController, appPreferencesProvider, songsProvider)
                }
                composable<Home> {
                    HomeScreen(navController, appPreferencesProvider,isDarkTheme, onToggleTheme)
                }
                composable<Search> { navBackStackEntry ->
                    val (stageId, categoryId) = navBackStackEntry.toRoute<Search>()
                    SearchScreen(
                        SearchFilters(stageId?.let { Stage.valueOf(it) },
                            categoryId?.let { Category.valueOf(it) }),
                        navController,
                        searchSongProvider
                    )
                }
                composable<Song> { backStackEntry ->
                    val songRoute: Song = backStackEntry.toRoute()
                    SongScreen(songRoute.id, songProvider, navController)
                }
                composable<Album> {
                    AlbumScreen()
                }
            }
        }
    }
}

