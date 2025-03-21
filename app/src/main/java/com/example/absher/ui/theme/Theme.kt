package com.example.absher.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF39836B),
    secondary = Color(0xFF757575),
    tertiary = Color(0xFF39836B),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color.White.copy(alpha = 0.7f),
    primaryContainer = Color(0xFF1B4D3E),
    onPrimaryContainer = Color.White,
    secondaryContainer = Color(0xFF454545),
    onSecondaryContainer = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF39836B),
    secondary = Color(0xFF757575),
    tertiary = Color(0xFF39836B),
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF0F0F0),
    onSurfaceVariant = Color.Black.copy(alpha = 0.7f),
    primaryContainer = Color(0xFFE1F5ED),
    onPrimaryContainer = Color(0xFF002117),
    secondaryContainer = Color(0xFFEEEEEE),
    onSecondaryContainer = Color(0xFF1C1B1F),
    error = Color(0xFFB00020),
    onError = Color.White
)

val Typography = androidx.compose.material3.Typography(

    /** Small regular text style (11sp), adapted from CustomTextStyles.SmallRegular11 */
    labelSmall = CustomTextStyles.SmallRegular11,
    /** Base bold text style (14sp), adapted from CustomTextStyles.BaseBold */
    displayMedium = CustomTextStyles.BaseBold,
    /** Small bold text style (12sp), adapted from CustomTextStyles.SmallBold */
    displaySmall = CustomTextStyles.SmallBold,
    /** Small regular text style (12sp), adapted from CustomTextStyles.SmallRegular12 */
    bodySmall = CustomTextStyles.SmallRegular12,
    /** Extra small bold underlined text (10sp), adapted from CustomTextStyles.XSmallBoldUnderLine */
    labelMedium = CustomTextStyles.XSmallBoldUnderLine,
    /** Extra small bold text (10sp), adapted from CustomTextStyles.XSmallBold */
    labelLarge = CustomTextStyles.XSmallBold
)


@Composable
fun AbsherTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) {
                Color(0xFF121212).toArgb()
            } else {
                colorScheme.primary.toArgb()
            }
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}