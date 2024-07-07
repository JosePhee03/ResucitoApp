package com.resucito.app.presentation.ui.screen.songbook.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.resucito.app.R

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopBarSongBook(isExpand: Boolean, onBackNavigate: () -> Unit, onExpand: () -> Unit) {

    Surface(
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isExpand) {
                onExpand()
            }
    ) {
        SharedTransitionLayout {
            AnimatedContent(targetState = isExpand, label = "aa") { targetState ->
                if (targetState) {
                    Row1(this@SharedTransitionLayout, this@AnimatedContent, onBackNavigate)
                } else {
                    Row2(this@SharedTransitionLayout, this@AnimatedContent, onBackNavigate)

                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Row2(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackNavigate: () -> Unit
) {
    with(sharedTransitionScope) {
        TopAppBar(
            modifier = Modifier
                .sharedBounds(
                    rememberSharedContentState(key = "topbar"),
                    animatedContentScope
                ),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            navigationIcon = {
                IconButton(onClick = { onBackNavigate() }) {
                    Icon(
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "IconArrow"),
                            animatedContentScope
                        ),
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = ""
                    )
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .sharedElement(
                                rememberSharedContentState(key = "Image"),
                                animatedContentScope
                            )
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(8.dp)
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onSecondaryContainer,
                            painter = painterResource(R.drawable.ic_favorite_filled),
                            contentDescription = stringResource(R.string.cover_image),
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(key = "Icon"),
                                animatedContentScope
                            )
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Favoritos",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "100 cantos",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            })
    }

}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Row1(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackNavigate: () -> Unit
) {
    with(sharedTransitionScope) {
        Column {
            TopAppBar(
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = "topbar"),
                        animatedContentScope
                    ),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                navigationIcon = {
                    IconButton(onClick = { onBackNavigate() }) {
                        Icon(
                            modifier = Modifier.sharedElement(
                                rememberSharedContentState(key = "IconArrow"),
                                animatedContentScope
                            ),
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = ""
                        )
                    }
                },
                title = {})

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "Image"),
                            animatedContentScope
                        )
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(16.dp)
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        painter = painterResource(R.drawable.ic_favorite_filled),
                        contentDescription = stringResource(R.string.cover_image),
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "Icon"),
                            animatedContentScope
                        )
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Favoritos",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "100 cantos",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

}