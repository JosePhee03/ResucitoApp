package com.resucito.app.presentation.ui.screen.songbook.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.resucito.app.R

const val TopAppBarMaxHeight = 144f
const val TopAppBarMinHeight = 64f
private val TopAppBarHorizontalPadding = 4.dp
private val TopAppBarElevation = 4.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSongBook(
    scrollBehavior: TopAppBarScrollBehavior,
    onBackNavigate: () -> Unit,
    songBookName: String,
    totalSongs: Int,
) {

    scrollBehavior.state.heightOffsetLimit = TopAppBarMinHeight - TopAppBarMaxHeight

    val collapsedFraction by remember {
        derivedStateOf { scrollBehavior.state.collapsedFraction }
    }

    val isCollapse by remember { derivedStateOf { collapsedFraction == 1f } }

    Surface(
        shadowElevation = if (isCollapse) TopAppBarElevation else 0.dp,
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .fillMaxWidth()
            .height(lerp(TopAppBarMaxHeight.dp, TopAppBarMinHeight.dp, collapsedFraction))
    ) {
        Box {
            Row(
                modifier = Modifier
                    .height(TopAppBarMinHeight.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column1(onBackNavigate)
                Column2(collapsedFraction, songBookName, totalSongs.toString())
            }
        }
    }
}

@Composable
fun Column1(onBackNavigate: () -> Unit) {
    Column(
        Modifier
            .padding(start = TopAppBarHorizontalPadding)
    ) {
        IconButton(onClick = { onBackNavigate() }) {
            Icon(
                tint = MaterialTheme.colorScheme.onSurface,
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.navigate_back)
            )
        }
    }
}

@Composable
fun Column2(fraction: Float, title: String, subtitle: String) {

    val offsetX = lerp((-38).dp, 0.dp, fraction)
    val offsetY = lerp(TopAppBarMinHeight.dp, 0.dp, fraction)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = TopAppBarHorizontalPadding)
            .offset(offsetX, offsetY)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(lerp(8.dp, 4.dp, fraction)))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(lerp(16.dp, 8.dp, fraction))
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                    painter = painterResource(R.drawable.ic_favorite_filled),
                    contentDescription = stringResource(R.string.cover_image),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = title,
                    lineHeight = lerp(28.sp, 16.sp, fraction),
                    fontSize = lerp(22.sp, 14.sp, fraction),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = subtitle,
                    lineHeight = lerp(20.sp, 16.sp, fraction),
                    fontSize = lerp(14.sp, 12.sp, fraction),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

