package com.resucito.app.presentation.ui.screen.song.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.resucito.app.R
import com.resucito.app.presentation.ui.components.FavoriteIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSong(
    favorite: Boolean,
    onChangeFavorite: (Boolean) -> Unit,
    onBackNavigate: () -> Unit
) {

    val scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(stringResource(R.string.song))
        },
        navigationIcon = {
            IconButton(onClick = { onBackNavigate() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.navigate_back)
                )
            }
        },
        actions = { FavoriteIconButton(favorite, onChangeFavorite) },
        scrollBehavior = scrollBehavior
    )
}