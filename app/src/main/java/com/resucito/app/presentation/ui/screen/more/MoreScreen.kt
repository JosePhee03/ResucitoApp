package com.resucito.app.presentation.ui.screen.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.resucito.app.R
import com.resucito.app.presentation.ui.components.Switch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(
    isDarkTheme: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {

    var showDialogLanguage by remember {
        mutableStateOf(false)
    }

    if (showDialogLanguage) {
        DialogLanguage(
            onDismissRequest = { showDialogLanguage = false },
            onConfirmation = { showDialogLanguage = false })
    }

    Column {
        TopAppBar(title = { Text(stringResource(R.string.more_options)) })

        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleTheme(!isDarkTheme) },
            headlineContent = { Text(stringResource(R.string.theme)) },
            supportingContent = {
                Text(if (isDarkTheme) stringResource(R.string.dark_mode) else stringResource(R.string.light_mode))
            },
            trailingContent = {
                Switch(
                    isChecked = isDarkTheme,
                    onCheckedChange = {
                        onToggleTheme(it)
                    }
                )
            }
        )

        ListItem(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDialogLanguage = true },
            headlineContent = { Text(stringResource(R.string.language)) },
            supportingContent = {
                Text(stringResource(R.string.spanish_language))
            }
        )
    }


}

@Composable
fun DialogLanguage(onDismissRequest: () -> Unit, onConfirmation: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    text = stringResource(R.string.select_language),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Column(modifier = Modifier.padding(vertical = 16.dp)) {

                    ListItem(
                        leadingContent = { RadioButton(selected = true, onClick = { }) },
                        headlineContent = { Text(stringResource(R.string.spanish_language)) })

                    Text(
                        text = stringResource(R.string.more_languages_coming_soon),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }


                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}