package com.resucito.app.presentation.ui.screen.start

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.resucito.app.R
import com.resucito.app.presentation.ui.navigation.Home
import kotlinx.coroutines.delay

@Composable
fun StartScreen(
    navController: NavHostController,
    onCreate: (Context, String) -> Unit,
    isLoading: Boolean,
    isError: Boolean,
    onToggleFirstRun: (Boolean) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(isLoading) {
        if (!isLoading && !isError) {
            onToggleFirstRun(true)
            navController.popBackStack()
            navController.navigate(route = Home)
        }
        delay(5000)
        onCreate(context, "ES_2019.json")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "Image de la palabra Resucitó",
                modifier = Modifier.size(400.dp)
            )

        }
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
        ) {
            if (isError && !isLoading) {
                Text(
                    text = "Error al cargar los datos",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(6.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    strokeCap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun StartScreenPreview() {
    StartScreen(
        rememberNavController(),
        { _, _ -> {} },
        true,
        false,
        { }
    )
}