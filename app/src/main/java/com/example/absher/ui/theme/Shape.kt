package com.example.absher.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AbsherShapes = Shapes(
    small = RoundedCornerShape(4.dp),  // For status and reference number boxes
    medium = RoundedCornerShape(8.dp), // For card
    large = RoundedCornerShape(12.dp)  // Larger shapes if needed
)