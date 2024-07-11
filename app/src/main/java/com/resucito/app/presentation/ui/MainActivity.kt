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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.resucito.app.presentation.ui.components.NavigationBottomBar
import com.resucito.app.presentation.ui.components.NavigationStatusBar
import com.resucito.app.presentation.ui.navigation.NavigationScreen
import com.resucito.app.presentation.ui.theme.ResucitoTheme
import com.resucito.app.presentation.viewmodel.ApplicationState
import com.resucito.app.presentation.viewmodel.ApplicationViewModel
import com.resucito.app.presentation.viewmodel.HomeScreenViewModel
import com.resucito.app.presentation.viewmodel.LibraryScreenViewModel
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel
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
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {

        val navController = rememberNavController()
        val snackBarController = remember { SnackbarHostState() }

        NavigationStatusBar(
            navController, applicationUiState.isDarkTheme
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
                NavigationScreen(
                    navController = navController,
                    snackBarController = snackBarController,
                    applicationViewModel = applicationViewModel,
                    applicationState = applicationUiState,
                    startScreenViewModel = startScreenViewModel,
                    homeScreenViewModel = homeScreenViewModel,
                    searchScreenViewModel = searchScreenViewModel,
                    songScreenViewModel = songScreenViewModel,
                    libraryScreenViewModel = libraryScreenViewModel
                )
            }
        }
    }

}



