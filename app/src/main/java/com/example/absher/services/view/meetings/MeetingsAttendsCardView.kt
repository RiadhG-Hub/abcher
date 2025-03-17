package com.example.absher.services.view.meetings


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absher.R
import com.example.absher.ui.theme.AbsherTheme


@Composable
fun MeetingsAttendsCardView(
    modifier: Modifier = Modifier, title: String, subtitle: String
) {Column {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(40.dp) // Adjust size as needed
                .clip(CircleShape) // Makes the image circular
                .border(BorderStroke(1.dp, Color(0XFFE5D9B4)), CircleShape) // Adds border
        ) {
            SvgIcon(drawable = R.drawable.man, modifier = Modifier
                .size(40.dp)
                .fillMaxSize())
        }
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = title, modifier = modifier.padding(bottom = 4.dp), style = TextStyle(

                    fontSize = 12.sp,
                    lineHeight = 20.sp,

                    fontWeight = FontWeight(700),
                    color = Color(0xFF808080),

                    textAlign = TextAlign.Right,

                    )
            )
            Text(
                text = subtitle, style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 20.sp,

                    fontWeight = FontWeight(400),
                    color = Color(0xFF353334),

                    textAlign = TextAlign.Right,
                )
            )
        }
    }
    HorizontalDivider()
}
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MeetingsAttendsCardView(title = "Title example ", subtitle = "Subtitle example")
        }
    }
}