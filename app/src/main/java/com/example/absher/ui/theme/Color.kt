package com.example.absher.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// Base Colors
val GreenPrimary = Color(0xFF39836B)
val SubtitleColor = Color(0xFF353334)
val Gray = Color(0xFF808080)
val MediumGray = Color(0xFFCBCBCB)
val BackgroundGray = Color(0xFFF5F5F5)
val TaskColor = Color(0xFF39836B)
val White = Color.White
val OutlinedButtonColor = Color(0xFF39836B)
val BackgroundGreen = Color(0xFFE1F5ED)

// Status Colors
val StatusGreen = Color(0xFF28A745)
val StatusYellow = Color(0xFFFFC107)
val StatusRed = Color(0xFFDC3545)
val StatusDefault = Color(0xFFD4A017)

// Semantic Colors - These adapt to light/dark theme
@Composable
fun contentBackground() = MaterialTheme.colorScheme.background

@Composable
fun surfaceColor() = MaterialTheme.colorScheme.surface

@Composable
fun primaryTextColor() = MaterialTheme.colorScheme.onSurface

@Composable
fun secondaryTextColor() = MaterialTheme.colorScheme.onSurfaceVariant

@Composable
fun dividerColor() = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f)

@Composable
fun cardBackgroundColor() = MaterialTheme.colorScheme.surface

@Composable
fun buttonBackgroundColor() = MaterialTheme.colorScheme.primary

@Composable
fun buttonTextColor() = MaterialTheme.colorScheme.onPrimary

// Semantic Status Colors
@Composable
fun statusGreenColor() = if (MaterialTheme.colorScheme.isLight()) StatusGreen else StatusGreen.copy(alpha = 0.8f)

@Composable
fun statusYellowColor() = if (MaterialTheme.colorScheme.isLight()) StatusYellow else StatusYellow.copy(alpha = 0.8f)

@Composable
fun statusRedColor() = if (MaterialTheme.colorScheme.isLight()) StatusRed else StatusRed.copy(alpha = 0.8f)

@Composable
fun statusDefaultColor() = if (MaterialTheme.colorScheme.isLight()) StatusDefault else StatusDefault.copy(alpha = 0.8f)

// Helper function to determine if we're in light mode
@Composable
private fun androidx.compose.material3.ColorScheme.isLight() = surface.luminance() > 0.5



