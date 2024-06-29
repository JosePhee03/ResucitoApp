package com.resucito.app.presentation.ui.screen.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.presentation.ui.theme.ThemeApp

@Composable
fun SearchBox(text: String, onChange: (String) -> Unit) {


    SearchBar(text) {
        onChange(it)
    }

}

@Composable
internal fun SearchBar(text: String, onChange: (String) -> Unit) {


    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        unfocusedContainerColor = ThemeApp.color.grey200,
        focusedContainerColor = ThemeApp.color.grey200,
    )

    Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        TextField(
            colors = textFieldColors,
            value = text,
            onValueChange = {
                onChange(it)
            },
            placeholder = { Text(stringResource(R.string.search_bar_placeholder)) },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = ""
                )
            },
            trailingIcon = {
                if (text != "") {
                    IconButton(
                        onClick = {
                            onChange("")
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.search_icon_close_description)
                        )
                    }
                }
            },
            shape = MaterialTheme.shapes.extraLarge,
            singleLine = true,
        )
    }


}