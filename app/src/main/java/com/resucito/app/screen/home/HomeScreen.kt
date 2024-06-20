package com.resucito.app.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun HomeScreen() {

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = {
            Text(
                text = "Resucitó",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }, actions = { IconButton(onClick = { /*TODO*/ }) { Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Cambiar el color del tema"
        )}})
        Text(text = "Índice de cantos")
    }
}