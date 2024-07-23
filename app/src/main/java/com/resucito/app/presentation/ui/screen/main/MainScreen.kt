package com.resucito.app.presentation.ui.screen.main

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.resucito.app.presentation.ui.components.NavigationBottomBar
import com.resucito.app.presentation.ui.navigation.MainRoute
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
    libraryScreenViewModel: LibraryScreenViewModel,
    isSecondaryStatusColor: (Boolean) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { 4 })

    val scope = rememberCoroutineScope()
    val snackBarController = remember { SnackbarHostState() }

    LaunchedEffect(pagerState.currentPage, isDarkTheme) {
        val isSecondaryColor =
            pagerState.currentPage == MainRoute.LIBRARY.page || pagerState.currentPage == MainRoute.MORE.page
        isSecondaryStatusColor(isSecondaryColor)
    }

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
                MainRoute.HOME.page -> HomeScreen(
                    navigateToSearch = { stageId, categoryId ->
                        searchScreenViewModel.setFiltersById(stageId, categoryId)
                        navController.navigate(
                            Routes.Main(
                                MainRoute.SEARCH.page
                            )
                        )
                    },
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = onToggleTheme,
                    vm = homeScreenViewModel
                )

                MainRoute.SEARCH.page -> SearchScreen(
                    navigateToSong = { navController.navigate(Routes.Song(it)) },
                    snackBarController = snackBarController,
                    vm = searchScreenViewModel
                )

                MainRoute.LIBRARY.page -> LibraryScreen(
                    vm = libraryScreenViewModel,
                    navigateToSongbook = { navController.navigate(Routes.SongBook) })

                MainRoute.MORE.page -> MoreScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = onToggleTheme
                )
            }
        }
    }
}