package com.resucito.app.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.presentation.ui.theme.ThemeApp

@Immutable
internal data class NavigationItem(
    val iconOutlined: Int,
    val iconFilled: Int,
    val label: String,
    val contentDescription: String
)

@Composable
fun NavigationBottomBar(page: () -> Int, onChangePage: (Int) -> Unit) {

    val homeLabel = stringResource(R.string.home)
    val searchLabel = stringResource(R.string.search)
    val listsLabel = stringResource(R.string.lists)
    val moreLabel = stringResource(R.string.more)
    val moreOptionsLabel = stringResource(R.string.more_options)

    val navigationItem = remember {
        listOf(
            NavigationItem(
                R.drawable.ic_home,
                R.drawable.ic_home_filled,
                homeLabel,
                homeLabel
            ),
            NavigationItem(
                R.drawable.ic_search,
                R.drawable.ic_search,
                searchLabel,
                searchLabel
            ),
            NavigationItem(
                R.drawable.ic_library_music,
                R.drawable.ic_library_music_filled,
                listsLabel,
                listsLabel
            ),
            NavigationItem(
                R.drawable.ic_more_horiz,
                R.drawable.ic_more_horiz,
                moreLabel,
                moreOptionsLabel
            )
        )
    }

    val change = remember<(Int) -> Unit> {
        { onChangePage(it) }
    }


    Column {
        HorizontalDivider(
            modifier = Modifier.height(1.dp), color = ThemeApp.color.grey300
        )
        NavigationBar(
            containerColor = Color.Transparent,
        ) {
            navigationItem.forEachIndexed { index, item ->

                val isSelected = remember(page()) {
                    page() == index
                }

                NavItem(
                    isSelected = { isSelected },
                    item = item,
                    onChangePage = { change(index) }
                )
            }
        }
    }

}

@Composable
private fun RowScope.NavItem(
    isSelected: () -> Boolean,
    onChangePage: () -> Unit,
    item: NavigationItem
) {

    val icon = remember(isSelected()) {
        if (isSelected()) item.iconFilled else item.iconOutlined
    }


    NavigationBarItem(
        label = { Text(item.label) },
        selected = isSelected(),
        onClick = {
            onChangePage()
        },
        icon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = item.contentDescription
            )
        })
}