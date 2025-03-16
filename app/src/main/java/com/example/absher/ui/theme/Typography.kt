package com.example.absher.ui.theme



import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

// Optional: Add custom font family if available
// val ArabicFontFamily = FontFamily(Font(R.font.noto_sans_arabic))

val AbsherTypography = Typography(
    // Clickable underlined text
    labelSmall = TextStyle(
        fontSize = 10.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    ),
    // Meeting ID
    titleLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    // Status text
    labelMedium = TextStyle(
        fontSize = 10.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    // Reference number
    bodySmall = TextStyle(
        fontSize = 10.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    // Date, Start/End Time
    bodyMedium = TextStyle(
        fontSize = 12.sp
    ),
    // Title/Notes
    bodyLarge = TextStyle(
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Bold
    )
)