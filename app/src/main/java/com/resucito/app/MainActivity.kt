package com.resucito.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.resucito.app.componets.NavigationBottomBar
import com.resucito.app.db.AppDatabase
import com.resucito.app.navigation.*
import com.resucito.app.screen.album.AlbumScreen
import com.resucito.app.screen.home.HomeScreen
import com.resucito.app.screen.search.SearchScreen
import com.resucito.app.screen.song.SongScreen
import com.resucito.app.screen.start.StartScreen
import com.resucito.app.ui.theme.ResucitoTheme
import com.resucito.app.viewmodel.SearchSongProvider
import com.resucito.app.viewmodel.SongProvider
import com.resucito.app.viewmodel.SongsProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { false }
        setContent {
            ResucitoTheme {
                val songDao = AppDatabase.getInstance(this).songDao()


                val songsProvider = SongsProvider(songDao)
                val songProvider = SongProvider(songDao)
                val searchSongProvider = SearchSongProvider(songDao)
                MainScreen(songsProvider, songProvider, searchSongProvider)
            }
        }
    }

}


@Composable
fun MainScreen(
    songsProvider: SongsProvider,
    songProvider: SongProvider,
    searchSongProvider: SearchSongProvider
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
                    StartScreen(navController, songsProvider)
                }
                composable<Home> {
                    HomeScreen()
                }
                composable<Search> {
                    SearchScreen(navController, searchSongProvider)
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

