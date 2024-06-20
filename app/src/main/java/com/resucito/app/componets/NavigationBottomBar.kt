package com.resucito.app.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import com.resucito.app.R
import com.resucito.app.navigation.Album
import com.resucito.app.navigation.Home
import com.resucito.app.navigation.Search

data class NavigationItem(
    val route: Any,
    val iconOutlined: ImageVector,
    val iconFilled: ImageVector,
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
            Icons.Outlined.Home,
            Icons.Filled.Home,
            "Inicio",
            "Icono de casa"
        ),
        NavigationItem(
            Search,
            Icons.Outlined.Search,
            Icons.Filled.Search,
            "Buscar",
            "Icono de lupa"
        ),
        NavigationItem(
            Album,
            Icons.Outlined.Star,
            Icons.Filled.Star,
            "Álbumes",
            "Icono de casa"
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
            color = colorResource(id = R.color.grey_300)
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
                            if (isSelected) navItem.iconFilled else navItem.iconOutlined,
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

@Composable
@Preview
fun NavigationBottomBarPreview() {


    val navigationItem = listOf(
        NavigationItem(
            Home,
            Icons.Outlined.Home,
            Icons.Filled.Home,
            "Inicio",
            "Icono de casa"
        ),
        NavigationItem(
            Search,
            Icons.Outlined.Search,
            Icons.Filled.Search,
            "Buscar",
            "Icono de lupa"
        ),
        NavigationItem(
            Album,
            Icons.Outlined.Star,
            Icons.Filled.Star,
            "Álbumes",
            "Icono de casa"
        )
    )

    NavigationBar(contentColor = MaterialTheme.colorScheme.background, tonalElevation = 16.dp) {
        navigationItem.forEach { navItem ->
            val isSelected =
                false

            NavigationBarItem(
                icon = {
                    Icon(
                        if (isSelected) navItem.iconFilled else navItem.iconOutlined,
                        contentDescription = navItem.contentDescription
                    )
                },
                label = { Text(navItem.label) },
                selected = isSelected,
                onClick = { }
            )
        }
    }

}


