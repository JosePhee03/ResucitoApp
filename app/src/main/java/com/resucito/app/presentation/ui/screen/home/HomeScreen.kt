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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.resucito.app.domain.model.Category
import com.resucito.app.domain.model.Stage
import com.resucito.app.presentation.ui.navigation.Search
import com.resucito.app.presentation.ui.screen.home.componets.CardCategory
import com.resucito.app.presentation.ui.screen.home.componets.CardStage
import com.resucito.app.presentation.ui.screen.home.componets.TopBarHome
import com.resucito.app.util.colorStage
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit,
    getCountStage: (Stage) -> Int,
    getCountCategory: (Category) -> Int
) {

    Column(modifier = Modifier.fillMaxSize()) {

        TopBarHome(isDarkTheme, onToggleTheme)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(160.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Espacio vertical entre elementos
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
                        text = "Índice de cantos",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Etapas del camino",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium

                )
            }

            Stage.entries.forEach {
                item {
                    CardStage(
                        it.name.lowercase(Locale.ENGLISH),
                        getCountStage(it),
                        colorStage(it).backgroundColor
                    ) {
                        navController.navigate(Search(it.name, null))
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Índice litúrgico",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium

                )
            }

            Category.entries.forEach {
                item {
                    CardCategory(
                        it.name.lowercase(Locale.ENGLISH),
                        getCountCategory(it)
                    ) {
                        navController.navigate(Search(null, it.name))
                    }
                }
            }
        }
    }
}


