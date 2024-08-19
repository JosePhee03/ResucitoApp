package com.resucito.app.presentation.ui.screen.start

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.presentation.viewmodel.StartScreenViewModel

@Composable
fun StartScreen(
    onRemoveStack: () -> Unit,
    navigateToHome: () -> Unit,
    onToggleFirstRun: (Boolean) -> Unit,
    vm: StartScreenViewModel
) {

    val uiState by vm.state.collectAsState()
    val isLoading = uiState.isLoading
    val isError = uiState.isError
    val onCreate = vm::onCreate

    LaunchedEffect(isLoading) {
        if (!isLoading && !isError) {
            onToggleFirstRun(false)
            onRemoveStack()
            navigateToHome()
        } else {
            onCreate("ES_2019.json")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.logo_description),
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
                    text = stringResource(R.string.error_loading_data),
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
