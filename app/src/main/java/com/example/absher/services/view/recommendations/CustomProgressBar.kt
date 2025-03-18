package com.example.absher.services.view.recommendations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.R
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.CustomTextStyles
import com.example.absher.ui.theme.GreenPrimary

@Composable
fun CustomProgressBar(
    progress: Float, // Progress as a percentage (0f to 100f)
    modifier: Modifier = Modifier,
    height: Dp = 16.dp,
    backgroundColor: Color = Color.Gray.copy(alpha = 0.3f),
    progressColor: Color = GreenPrimary,
    cornerRadius: Dp = 4.dp,

) {
    // Convert progress from percentage (0-100) to fraction (0-1)
    val progressFraction = (progress / 100f).coerceIn(0f, 1f)

    Row(verticalAlignment= Alignment.CenterVertically,){

        SvgIcon(R.drawable.work_history,)
        Box(
            modifier = modifier.padding(start = 8.dp)
                .height(height)
                .fillMaxWidth()



        ){

            Box(
                modifier = modifier.padding(start = 8.dp)
                    .height(height)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(backgroundColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressFraction) // Use the converted fraction
                        .height(height)
                        .background(progressColor)
                )
            }

            Row(
                modifier = modifier.padding(start = 16.dp, end = 16.dp).fillMaxWidth(),
                horizontalArrangement =Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,  ){

                Text(text = "مستوى التقدم" , style = CustomTextStyles.XSmallBold.copy(Color.White))
                Text(text = "$progress %" , style = CustomTextStyles.XSmallBold.copy(Color.White))

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CustomProgressBarPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    CustomProgressBar(progress = 100F)

        }}
}