package com.resucito.app.presentation.ui.screen.song.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resucito.app.R
import com.resucito.app.domain.model.Chord
import com.resucito.app.presentation.ui.theme.ThemeApp


@Composable
@Preview(showBackground = true)
fun LyricContainerPreview() {

    Column {
        val lyric =
            "  Dm\nS. ACLAMAD AL SEÑOR  \n Gm             Dm\nTODA LA TIERRA,\n          F                 G                        A\nSERVID AL SEÑOR CON ALEGRÍA.\n\n            Dm        A7\nS. Acercaos a él  \n                                 Dm\ncon gritos de júbilo.\n\nA. ACLAMAD AL SEÑOR ... \n\n                            Dm                          A7\nS. Porque el Señor es nuestro Dios,  \n                                     Dm\ny nosotros su pueblo.\n\nA. ACLAMAD AL SEÑOR ... \n\n       Dm                             A7\nS. Entrad por sus puertas  \n                                          Dm\ncon himnos de alegría.\n\nA. ACLAMAD AL SEÑOR ...\n\n              Dm                  A7\nS. Bendecid su nombre,  \n                             Dm\ndándole gracias.\n\nA. ACLAMAD AL SEÑOR ... \n\n                          Dm                A7\nS. Porque es bueno el Señor,  \n                                                             Dm\nes eterno su amor con nosotros.\n\nA. ACLAMAD AL SEÑOR ..."
        lyric.split("\n").forEach {
            LyricContainer(it)
        }
    }

}

@Composable
fun LyricContainer(part: String) {

    val uppercaseLettersRegex = Regex("[A-Z]{2,}")
    val chords =part.replace("/", " ").replace("*", " ").trim().split("\\s+".toRegex()).toTypedArray()
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
    )
    Row(modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()) {
        if (Chord.validChordArray(chords)) {

            Text(
                text = "     $part",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1
            )
        } else if (part.isBlank()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
        } else {
            val (prefix, letter) = separatePrefixFromText(part)
            Text(
                text = prefix,
                color = ThemeApp.color.grey600,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )

            val splitLetter = letter.trimEnd().split("  ")
            if (uppercaseLettersRegex.containsMatchIn(letter)) {

                if (splitLetter.size == 2) {
                    Text(
                        text = "${splitLetter[0]} ",
                        fontWeight = FontWeight.Normal,
                        maxLines = 1
                    )
                    Text(
                        text = splitLetter[1],
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                } else {
                    Text(
                        text = letter,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }

            } else {
                Text(
                    text = letter,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
            }

        }
    }
}

fun separatePrefixFromText(text: String): Pair<String, String> {
    val regex = Regex("""(^[A-Z][0-9]\. )|(^[A-Z]\. [A-Z]\. )|(^[A-Z]\. )""")
    val match = regex.find(text)

    return if (match != null) {
        val prefix = match.groupValues[0]
        val remainingText =
            text.substring(match.range.last + 1) // Get the remaining text after prefix
        Pair(prefix, remainingText)
    } else {
        Pair("     ", text)
    }
}

