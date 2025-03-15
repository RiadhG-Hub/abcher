package com.example.absher.services.view


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.services.view.ui.theme.AbsherTheme
import com.example.absher.R


@Composable
fun MeetingsAttendsCardView( modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
        Box(
            modifier = Modifier
                .size(40.dp) // Adjust size as needed
                .clip(CircleShape) // Makes the image circular
                .border(BorderStroke(1.dp, Color(0XFFE5D9B4)), CircleShape) // Adds border
        ) {
        SvgIcon(drawable = R.drawable.man , modifier =  Modifier.size(40.dp).fillMaxSize(), )
}
        Column {
            Text(text = "عبدالله بن محمد")
            Text(text = "oaadiab@hq.moi.gov.sa")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MeetingsAttendsCardView()}
    }
}