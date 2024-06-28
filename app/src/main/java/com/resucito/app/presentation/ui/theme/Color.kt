package com.resucito.app.presentation.ui.theme

import androidx.compose.ui.graphics.Color

object LightModeColors {
    val primary = Color(0xFFF80000)
    val onPrimary = Color(0xFFFFFFFF)
    val primaryContainer = Color(0xFFE69D9D)
    val onPrimaryContainer = Color(0xFF330000)
    val secondary = Color(0xFFA90000)
    val onSecondary = Color(0xFFFFFFFF)
    val secondaryContainer = Color(0xFFE69D9D)
    val onSecondaryContainer = Color(0xFF330000)
    val tertiary = Color(0xFFFF8888)
    val onTertiary = Color(0xFFFFFFFF)
    val tertiaryContainer = Color(0xFFE6C3C3)
    val onTertiaryContainer = Color(0xFF331B1B)
    val error = Color(0xFFDC362E)
    val onError = Color(0xFFFFFFFF)
    val errorContainer = Color(0xFFE6AFAC)
    val onErrorContainer = Color(0xFF330D0B)
    val background = Color(0xFFFFF8F6)
    val onBackground = Color(0xFF333030)
    val surface = Color(0xFFFFF8F6)
    val onSurface = Color(0xFF333030)
    val surfaceVariant = Color(0xFFE6D7D7)
    val onSurfaceVariant = Color(0xFF665252)
    val outline = Color(0xFF333333)
    val outlineVariant = Color(0xFFD9D9D9)
}

object DarkModeColors {
    val primary = Color(0xFFE67F7F)
    val onPrimary = Color(0xFF4C0000)
    val primaryContainer = Color(0xFF30660000)
    val onPrimaryContainer = Color(0xFFE69D9D)
    val secondary = Color(0xFFE67F7F)
    val onSecondary = Color(0xFF4C0000)
    val secondaryContainer = Color(0xFF660000)
    val onSecondaryContainer = Color(0xFFE69D9D)
    val tertiary = Color(0xFFE6ACB2)
    val onTertiary = Color(0xFF4C2226)
    val tertiaryContainer = Color(0xFF662D33)
    val onTertiaryContainer = Color(0xFFE6BDC1)
    val error = Color(0xFFE69490)
    val onError = Color(0xFF4C100D)
    val errorContainer = Color(0xFF661511)
    val onErrorContainer = Color(0xFFE6ACA9)
    val background = Color(0xFF333030)
    val onBackground = Color(0xFFE6E2E2)
    val surface = Color(0xFF333030)
    val onSurface = Color(0xFFE6E2E2)
    val surfaceVariant = Color(0xFF665252)
    val onSurfaceVariant = Color(0xFFE6D1D1)
    val outline = Color(0xFF4C4C4C)
    val outlineVariant = Color(0xFFB6B6B6)
}

val colorThemeLight = ColorTheme(
    precatechumenate = Color(0xFFCCCCCC),
    onPrecatechumenate = Color(0xFFFFFFFF),
    precatechumenateContainer = Color(0xFFE6E6E6),
    onPrecatechumenateContainer = Color(0xFF333333),

    catechumenate = Color(0xFF81D4FA),
    onCatechumenate = Color(0xFFCCDDFF),
    catechumenateContainer = Color(0xFFBEE9FF),
    onCatechumenateContainer = Color(0xFF001F2A),

    liturgy = Color(0xFFFDEC54),
    onLiturgy = Color(0xFFFFFFFF),
    liturgyContainer = Color(0xFFE6E0B5),
    onLiturgyContainer = Color(0xFF332F11),

    election = Color(0xFFC3F2C2),
    onElection = Color(0xFFFFFFFF),
    electionContainer = Color(0xFFC7F6C6),
    onElectionContainer = Color(0xFF2C5531),

    grey50 = Color(0xFFFAFAFA),
    grey100 = Color(0xFFF5F5F5),
    grey200 = Color(0xFFEEEEEE),
    grey300 = Color(0xFFE0E0E0),
    grey400 = Color(0xFFBDBDBD),
    grey500 = Color(0xFF9E9E9E),
    grey600 = Color(0xFF757575),
    grey700 = Color(0xFF616161),
    grey800 = Color(0xFF424242),
    grey900 = Color(0xFF212121)
)

val colorThemeDark = ColorTheme(
    precatechumenate = Color(0xFFE6E6E6),
    onPrecatechumenate = Color(0xFF4C4C4C),
    precatechumenateContainer = Color(0xFF666666),
    onPrecatechumenateContainer = Color(0xFFE6E6E6),

    catechumenate = Color(0xFFB4D6E6),
    onCatechumenate = Color(0xFF28414C),
    catechumenateContainer = Color(0xFF004D65),
    onCatechumenateContainer = Color(0xFFBEE9FF),

    liturgy = Color(0xFFE6DEA1),
    onLiturgy = Color(0xFF4C4719),
    liturgyContainer = Color(0xFF665F22),
    onLiturgyContainer = Color(0xFFE6E0B5),

    election = Color(0xFFD2E6D1),
    onElection = Color(0xFF3E4C3D),
    electionContainer = Color(0xFF1E5127),
    onElectionContainer = Color(0xFFB8F1B9),

    grey50 = Color(0xFF303030),
    grey100 = Color(0xFF424242),
    grey200 = Color(0xFF616161),
    grey300 = Color(0xFF757575),
    grey400 = Color(0xFF9E9E9E),
    grey500 = Color(0xFFBDBDBD),
    grey600 = Color(0xFFE0E0E0),
    grey700 = Color(0xFFEEEEEE),
    grey800 = Color(0xFFF5F5F5),
    grey900 = Color(0xFFFAFAFA)
)
data class ColorTheme(
    val precatechumenate: Color,
    val onPrecatechumenate: Color,
    val precatechumenateContainer: Color,
    val onPrecatechumenateContainer: Color,

    val catechumenate: Color,
    val onCatechumenate: Color,
    val catechumenateContainer: Color,
    val onCatechumenateContainer: Color,

    val liturgy: Color,
    val onLiturgy: Color,
    val liturgyContainer: Color,
    val onLiturgyContainer: Color,

    val election: Color,
    val onElection: Color,
    val electionContainer: Color,
    val onElectionContainer: Color,

    val grey50: Color,
    val grey100: Color,
    val grey200: Color,
    val grey300: Color,
    val grey400: Color,
    val grey500: Color,
    val grey600: Color,
    val grey700: Color,
    val grey800: Color,
    val grey900: Color,
)