package com.resucito.app.presentation.ui.screen.home.componets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.resucito.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarHome (isDarkTheme: Boolean, onToggleTheme: (Boolean) -> Unit) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }, actions = {
        IconButton(onClick = { onToggleTheme(!isDarkTheme) }) {
            Icon(
                painter = painterResource(if (isDarkTheme) R.drawable.ic_sun else R.drawable.ic_moon),
                contentDescription = stringResource(if (isDarkTheme) R.string.switch_to_light_theme else R.string.switch_to_dark_theme)
            )
        }
    })
}