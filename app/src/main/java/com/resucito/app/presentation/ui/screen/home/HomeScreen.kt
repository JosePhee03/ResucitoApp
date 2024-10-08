package com.resucito.app.presentation.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage
import com.resucito.app.presentation.ui.screen.home.components.CardCategory
import com.resucito.app.presentation.ui.screen.home.components.CardStage
import com.resucito.app.presentation.ui.screen.home.components.TopBarHome
import com.resucito.app.presentation.viewmodel.HomeScreenViewModel
import com.resucito.app.util.GetStringResource
import com.resucito.app.util.colorStage


@Composable
fun HomeScreen(
    navigateToSearch: (String?, String?) -> Unit,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit,
    vm: HomeScreenViewModel
) {

    val getCountStage = remember<(Stage) -> Int> {
        { vm.getCountStage(it) }
    }

    val getCountCategory = remember<(Category) -> Int> {
        { vm.getCountCategory(it) }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TopBarHome(isDarkTheme, onToggleTheme)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.index_songs),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.Stages_of_the_journey),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium

                )
            }

            items(Stage.entries) {
                CardStage(
                    GetStringResource.getLocalizedName(it),
                    getCountStage(it),
                    colorStage(it).backgroundColor
                ) {
                    navigateToSearch(it.name, null)
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.liturgical_index),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium

                )
            }

            items(Category.entries) {
                CardCategory(
                    GetStringResource.getLocalizedName(it), getCountCategory(it)
                ) {
                    navigateToSearch(null, it.name)
                }
            }
        }
    }
}


