package com.resucito.app.screen.search.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resucito.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(searchSong: (String) -> Unit) {

    var text by remember {
        mutableStateOf("")
    }

    val itemSuggestions = listOf("AAA", "BBB", "CCC")

    val searchBarColors = SearchBarDefaults.colors(
        containerColor = colorResource(id = R.color.grey_200),
        dividerColor = colorResource(id = R.color.grey_300),
    )

    LaunchedEffect(Unit) {
        searchSong(text)
    }

    Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            colors = searchBarColors,
            query = text,
            onQueryChange = { text = it },
            active = false,
            onActiveChange = {},
            onSearch = {
                searchSong(it)
            },
            placeholder = { Text("Hacia ti morada santa") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Outlined.Search, "Icono de lupa")

                }

            },
            trailingIcon = {

                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Limpiar Busqueda"
                    )
                }

            }
        ) {
            itemSuggestions.forEach {
                Row(modifier = Modifier.padding(16.dp)) {
                    Icon(
                        modifier = Modifier.padding(end = 8.dp),
                        imageVector = Icons.Default.Check,
                        contentDescription = it
                    )
                    Text(text = it)
                }
            }
        }
    }

}

@Composable
@Preview
internal fun SearchBar() {

    var text by remember {
        mutableStateOf("")
    }

    Row {


        TextField(value = text, onValueChange = { text = it },
            placeholder = { Text("Hacia ti morada santa") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Outlined.Search, "Icono de lupa")

                }

            },
            trailingIcon = {

                IconButton(onClick = { text = "" }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Limpiar Busqueda"
                    )
                }

            })


    }

}