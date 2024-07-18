package com.resucito.app.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.resucito.app.presentation.ui.navigation.NavigationScreen
import com.resucito.app.presentation.ui.theme.ResucitoTheme
import com.resucito.app.presentation.viewmodel.ApplicationState
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

    private val applicationViewModel: ApplicationViewModel by viewModels()
    private val startScreenViewModel: StartScreenViewModel by viewModels()
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()
    private val searchScreenViewModel: SearchScreenViewModel by viewModels()
    private val songScreenViewModel: SongScreenViewModel by viewModels()
    private val songBookScreenViewModel: SongBookScreenViewModel by viewModels()
    private val libraryScreenViewModel: LibraryScreenViewModel by viewModels()
    private lateinit var applicationUiState: ApplicationState

    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        screenSplash.setKeepOnScreenCondition { false }
        setContent {
            val isSystemInDarkTheme = isSystemInDarkTheme()

            applicationUiState = applicationViewModel.state.collectAsState(
                initial = ApplicationState(
                    isDarkTheme = applicationViewModel.getIsDarkTheme(isSystemInDarkTheme),
                    isFirstRun = applicationViewModel.getIsFirstRun()
                )
            ).value

            ResucitoTheme(applicationUiState.isDarkTheme) {
                Main()
            }
        }
    }

    @Composable
    fun Main() {

        val navController = rememberNavController()
        val snackBarController = remember { SnackbarHostState() }

        NavigationScreen(
            navController,
            snackBarController,
            applicationViewModel,
            applicationUiState,
            startScreenViewModel,
            homeScreenViewModel,
            searchScreenViewModel,
            songScreenViewModel,
            songBookScreenViewModel,
            libraryScreenViewModel
        )
    }

}



