package com.resucito.app.screen.home

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.resucito.app.R
import com.resucito.app.data.Category
import com.resucito.app.data.Stage
import com.resucito.app.navigation.Search
import com.resucito.app.screen.home.componets.CardCategory
import com.resucito.app.screen.home.componets.CardStage
import com.resucito.app.util.colorStage
import com.resucito.app.viewmodel.AppPreferencesProvider
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    appPreferencesProvider: AppPreferencesProvider,
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = {
            Text(
                text = "Resucitó",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }, actions = {
            IconButton(onClick = { onToggleTheme(!isDarkTheme) }) {
                Icon(
                    painter = painterResource(if (isDarkTheme) R.drawable.ic_sun else R.drawable.ic_moon),
                    contentDescription = "Cambiar el color del tema"
                )
            }
        })
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
                        appPreferencesProvider.getStagePreferences(it),
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
                        appPreferencesProvider.getCategoryPreferences(it)
                    ) {
                        navController.navigate(Search(null, it.name))
                    }
                }
            }
        }
    }
}


