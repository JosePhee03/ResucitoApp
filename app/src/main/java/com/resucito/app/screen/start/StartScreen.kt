package com.resucito.app.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.resucito.app.R
import com.resucito.app.data.Category
import com.resucito.app.data.Stage
import com.resucito.app.navigation.Home
import com.resucito.app.screen.search.readJsonFromAssets
import com.resucito.app.viewmodel.AppPreferencesProvider
import com.resucito.app.viewmodel.SongsProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StartScreen(
    navController: NavHostController,
    appPreferencesProvider: AppPreferencesProvider,
    songsProvider: SongsProvider
) {

    val context = LocalContext.current
    var databaseInitialized by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(databaseInitialized) {
        if (!databaseInitialized) {
            scope.launch(Dispatchers.IO) {
                val jsonString = readJsonFromAssets(context, "ES_2019.json")
                songsProvider.insertFromJson(jsonString)
                setCountSongsByStage(appPreferencesProvider, songsProvider)
                setCountSongsByCategory(appPreferencesProvider, songsProvider)
                databaseInitialized = true
            }
        } else {
            navController.popBackStack()
            navController.navigate(route = Home)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Example Image",
                modifier = Modifier.size(400.dp)
            )

        }
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp)
            )


        }
    }
}

internal suspend fun setCountSongsByStage(
    appPreferencesProvider: AppPreferencesProvider,
    songsProvider: SongsProvider
) {
    Stage.entries.forEach {
        val count = songsProvider.countSongsByStage(it)
        appPreferencesProvider.updateStagePreferences(it, count)
    }

}

internal suspend fun setCountSongsByCategory(
    appPreferencesProvider: AppPreferencesProvider,
    songsProvider: SongsProvider
) {
    Category.entries.forEach {
        val count = songsProvider.countSongsByCategory(it)
        println("COUNT $count - ${it.name}")
        appPreferencesProvider.updateCategoryPreferences(it, count)
    }
}