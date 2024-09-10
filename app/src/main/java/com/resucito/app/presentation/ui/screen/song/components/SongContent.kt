package com.resucito.app.presentation.ui.screen.song.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resucito.app.R
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Song
import com.resucito.app.domain.model.Stage
import com.resucito.app.presentation.ui.components.SuggestionChipCategory
import com.resucito.app.presentation.ui.components.SuggestionChipStage

@Composable
fun SongContent(song: Song, navigateToSearch: (String?, String?) -> Unit) {

    LazyColumn(Modifier.padding(bottom = 24.dp)) {

        chipSong(song.stage, song.categories, navigateToSearch)

        pageText(song.page)

        capoText(song.capo)

        chordsText(song.chords)

        titleText(song.title)

        subtitleText(song.subtitle)

        song.lyric.split("\n").forEach {
            item {
                LyricContainer(it)
            }
        }
    }


}

fun LazyListScope.chipSong(
    stage: Stage,
    categories: List<Category>,
    navigateToSearch: (String?, String?) -> Unit
) {
    item {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                SuggestionChipStage(
                    stage = stage,
                    onClick = { navigateToSearch(it.name, null) })
            }
            items(categories) {
                SuggestionChipCategory(
                    category = it,
                    onClick = { category -> navigateToSearch(null, category.name) })
            }
        }
    }
}

internal fun LazyListScope.pageText(page: Int) {
    item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 24.dp)),
            text = page.toString(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun capoTextBuilder(capo: Int) = buildAnnotatedString {
    append(stringResource(R.string.capo))
    withStyle(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
        )
    ) {
        append(" ${capo}° ")
    }
    append(stringResource(R.string.fret))
}


internal fun LazyListScope.capoText(capo: Int) {

    item {
        Text(
            text = capoTextBuilder(capo),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 0.dp,
                    bottom = 8.dp
                )
            ),
        )
    }
}

@Composable
internal fun chordsTextBuilder(chords: List<String>) = buildAnnotatedString {
    append(stringResource(R.string.chords))
    withStyle(
        style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            letterSpacing = 2.sp,
        )
    ) {
        append(" $chords")
    }
}

internal fun LazyListScope.chordsText(chords: List<String>) {

    item {
        Text(
            modifier = Modifier.padding(
                PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 0.dp,
                    bottom = 24.dp
                )
            ),
            text = chordsTextBuilder(chords),
            fontWeight = FontWeight.Bold
        )
    }
}

internal fun LazyListScope.titleText(title: String) {
    item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = title.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
    }
}

internal fun LazyListScope.subtitleText(subtitle: String) {
    item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 24.dp)),
            text = subtitle,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
    }
}