package com.resucito.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.resucito.app.R

val AppFontFamily = FontFamily(
    Font(R.font.franklin_regular),
    Font(R.font.franklin_bold, FontWeight.Bold),
    Font(R.font.franklin_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.franklin_medium, FontWeight.Medium),
    Font(R.font.franklin_semibold, FontWeight.SemiBold)
)

val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 48.sp,
        lineHeight = 56.sp
    ),
    displayMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 36.sp,
        lineHeight = 42.sp
    ),
    displaySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold
    ),

    titleLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),

    labelLarge = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp
    ),
    labelSmall = TextStyle(
        fontFamily = AppFontFamily,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.1.sp
    )
)

val AppTypography = typography