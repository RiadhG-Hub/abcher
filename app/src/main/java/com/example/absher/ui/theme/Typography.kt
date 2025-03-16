package com.example.absher.ui.theme



import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    ,

)


// Custom text styles object
object CustomTextStyles {
    val SmallRegular11 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

    val BaseBold = TextStyle(
    fontSize = 14.sp,
    lineHeight = 24.sp,
    //fontFamily = FontFamily(Font(R.font.almarai)),
    fontWeight = FontWeight(700),
    color = Color(0xFFFFFFFF),
    textAlign = TextAlign.Right,
    )

    val SmallBold =  TextStyle(
        fontSize = 12.sp,
        lineHeight = 20.sp,
        //fontFamily = FontFamily(Font(R.font.almarai)),
        fontWeight = FontWeight(700),
        color = Color(0xFFC3A355),

        textAlign = TextAlign.Right,
    )
    val SmallRegular12 = TextStyle(
        fontSize = 12.sp,
        lineHeight = 20.sp,

        fontWeight = FontWeight(400),
        color = Color(0xFF353334),

        textAlign = TextAlign.Right,
    )

   val  XSmallBoldUnderLine=  TextStyle(
    fontSize = 10.sp,
    lineHeight = 16.sp,

    fontWeight = FontWeight(700),
    color = Color(0xFF39836B),

    textDecoration = TextDecoration.Underline,
    )

}