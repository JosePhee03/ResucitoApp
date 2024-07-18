package com.resucito.app.presentation.ui.screen.main

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.resucito.app.presentation.ui.components.NavigationBottomBar
import com.resucito.app.presentation.ui.navigation.Routes
import com.resucito.app.presentation.ui.screen.home.HomeScreen
import com.resucito.app.presentation.ui.screen.library.LibraryScreen
import com.resucito.app.presentation.ui.screen.more.MoreScreen
import com.resucito.app.presentation.ui.screen.search.SearchScreen
import com.resucito.app.presentation.viewmodel.HomeScreenViewModel
import com.resucito.app.presentation.viewmodel.LibraryScreenViewModel
import com.resucito.app.presentation.viewmodel.SearchScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    initialPage: Int,
    navController: NavHostController,
    homeScreenViewModel: HomeScreenViewModel,
    onToggleTheme: (Boolean) -> Unit,
    isDarkTheme: Boolean,
    searchScreenViewModel: SearchScreenViewModel,
    libraryScreenViewModel: LibraryScreenViewModel
) {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { 4 })

    val scope = rememberCoroutineScope()
    val snackBarController = remember { SnackbarHostState() }


    Scaffold(
        bottomBar = {
            NavigationBottomBar(
                page = { pagerState.settledPage },
                onChangePage = { scope.launch { pagerState.scrollToPage(it) } })
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            contentPadding = paddingValues
        ) { currentPage ->
            when (currentPage) {
                0 -> HomeScreen(
                    navigateToSearch = { _, _ ->
                        navController.navigate(
                            Routes.Search(
                                null,
                                null
                            )
                        )
                    },
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = onToggleTheme,
                    vm = homeScreenViewModel
                )

                1 -> SearchScreen(
                    navigateToSong = { navController.navigate(Routes.Song(it)) },
                    stageId = null,
                    categoryId = null,
                    snackBarController = snackBarController,
                    vm = searchScreenViewModel
                )

                2 -> LibraryScreen(
                    vm = libraryScreenViewModel,
                    navigateToSongbook = { navController.navigate(Routes.SongBook) })

                3 -> MoreScreen(isDarkTheme = isDarkTheme, onToggleTheme = onToggleTheme)
            }
        }
    }
}