package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.resucito.app.R
import com.resucito.app.presentation.ui.navigation.Routes
import com.resucito.app.presentation.ui.theme.ThemeApp

internal data class NavigationItem(
    val route: Routes,
    val iconOutlined: Int,
    val iconFilled: Int,
    val label: String,
    val contentDescription: String
)

@Stable
internal data class ListNavigationItems(
    val items: List<NavigationItem> = emptyList()
)


@Composable
fun NavigationBottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val navDestination = navBackStackEntry?.destination

    val navigationItem = ListNavigationItems(
        items = listOf(
            NavigationItem(
                Routes.Home,
                R.drawable.ic_home,
                R.drawable.ic_home_filled,
                stringResource(R.string.home),
                stringResource(R.string.home)
            ), NavigationItem(
                Routes.Search(null, null),
                R.drawable.ic_search,
                R.drawable.ic_search,
                stringResource(R.string.search),
                stringResource(R.string.search)
            ), NavigationItem(
                Routes.Library,
                R.drawable.ic_library_music,
                R.drawable.ic_library_music_filled,
                stringResource(R.string.lists),
                stringResource(R.string.lists)
            ), NavigationItem(
                Routes.More,
                R.drawable.ic_more_horiz,
                R.drawable.ic_more_horiz,
                stringResource(R.string.more),
                stringResource(R.string.more_options)
            )
        )
    )

    if (navDestination == null) return
    else if (navigationItem.items.any { navItem -> navController.graph[navItem.route] == navDestination }) {
        BottomBar(navController, navigationItem, navDestination)
    } else return

}

@Composable
private fun BottomBar(
    navController: NavHostController,
    navigationItem: ListNavigationItems,
    navDestination: NavDestination
) {
    Column {
        HorizontalDivider(
            modifier = Modifier.height(1.dp), color = MaterialTheme.colorScheme.outline
        )
        NavigationBar(
            containerColor = Color.Transparent,
        ) {
            navigationItem.items.forEach { navItem ->
                val isSelected = navDestination == navController.graph[navItem.route]

                NavigationBarItem(label = { Text(navItem.label) },
                    selected = isSelected,
                    onClick = { navController.navigate(route = navItem.route) },
                    icon = {
                        Icon(
                            painterResource(if (isSelected) navItem.iconFilled else navItem.iconOutlined),
                            contentDescription = navItem.contentDescription
                        )
                    })
            }
        }
    }

}


