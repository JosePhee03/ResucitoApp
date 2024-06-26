package com.resucito.app.presentation.ui.screen.search.componets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import kotlinx.coroutines.delay

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
        unfocusedContainerColor = colorResource(id = R.color.grey_200),
        focusedContainerColor = colorResource(id = R.color.grey_200),
    )

    Box(modifier = Modifier.padding(horizontal =  16.dp, vertical = 8.dp)) {
        TextField(
            colors = textFieldColors,
            value = text,
            onValueChange = {
                onChange(it)
            },
            placeholder = { Text(text = "Hacia ti morada santa")},
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
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
                            Icons.Default.Close,
                            contentDescription = ""
                        )
                    }
                }
            },
            shape = MaterialTheme.shapes.extraLarge,
            singleLine = true,
        )
    }


}