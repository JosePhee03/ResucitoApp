package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.resucito.app.R
import com.resucito.app.presentation.ui.navigation.Album
import com.resucito.app.presentation.ui.navigation.Home
import com.resucito.app.presentation.ui.navigation.More
import com.resucito.app.presentation.ui.navigation.Search
import com.resucito.app.presentation.ui.theme.ThemeApp

data class NavigationItem(
    val route: Any,
    val iconOutlined: Int,
    val iconFilled: Int?,
    val label: String,
    val contentDescription: String
)


@Composable
fun NavigationBottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val navDestination = navBackStackEntry?.destination

    val navigationItem = listOf(
        NavigationItem(
            Home,
            R.drawable.ic_home,
            R.drawable.ic_home_filled,
            "Inicio",
            "Icono de casa"
        ),
        NavigationItem(
            Search(null, null),
            R.drawable.ic_search,
            null,
            "Buscar",
            "Icono de lupa"
        ),
        NavigationItem(
            Album,
            R.drawable.ic_library_music,
            R.drawable.ic_library_music_filled,
            "Álbumes",
            "Icono de albumes de musica"
        ),
        NavigationItem(
            More,
            R.drawable.ic_more_horiz,
            null,
            "Más",
            "Icono de 3 puntitos horizontal"
        )
    )

    if (navDestination == null) return
    else if (navigationItem.any { navItem -> navController.graph[navItem.route] == navDestination }) {
        BottomBar(navController, navigationItem, navDestination)
    } else return

}

@Composable
fun BottomBar(
    navController: NavHostController,
    navigationItem: List<NavigationItem>,
    navDestination: NavDestination
) {
    Column {
        HorizontalDivider(
            modifier = Modifier
                .height(1.dp),
            color = ThemeApp.color.grey300
        )
        NavigationBar(
            containerColor = Color.Transparent,
        ) {
            navigationItem.forEach { navItem ->
                val isSelected =
                    navDestination == navController.graph[navItem.route]

                NavigationBarItem(
                    icon = {
                        Icon(
                            painterResource(if (isSelected && navItem.iconFilled != null) navItem.iconFilled else navItem.iconOutlined),
                            contentDescription = navItem.contentDescription
                        )
                    },
                    label = { Text(navItem.label) },
                    selected = isSelected,
                    onClick = { navController.navigate(route = navItem.route) }
                )
            }
        }
    }

}


