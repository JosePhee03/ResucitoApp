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
    stageId: String? = null,
    categoryId: String? = null,
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
                    vm = homeScreenViewModel
                )

                1 -> SearchScreen(
                    navigateToSong = { navController.navigate(Routes.Song(it)) },
                    stageId = stageId,
                    categoryId = categoryId,
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