package com.resucito.app.presentation.ui.screen.album.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resucito.app.R


@Composable
fun ItemAlbumSongs(name: String, count: Int) {

    ListItem(
        modifier = Modifier.fillMaxWidth(),
        leadingContent = {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.Red)
                    .padding(16.dp)
            ) {
                Icon(
                    tint = Color(0xFF5C0029),
                    painter = painterResource(R.drawable.ic_favorite_filled),
                    contentDescription = "Imagen del album"
                )
            }

        },
        headlineContent = { Text(name) },
        supportingContent = {
            Text("$count cantos")
        }
    )
}

@Composable
@Preview(showBackground = true)
fun ItemAlbumSongsPreview () {
    ItemAlbumSongs("Favoritos", 100)
}