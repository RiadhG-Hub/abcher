package com.example.absher.services.view.meetings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MeetingAgendaCardView(text: String) {

    Text(modifier = Modifier.padding(8.dp),
        text = text, style = TextStyle(
            fontSize = 12.sp,
            lineHeight = 20.sp,

            fontWeight = FontWeight(400),
            color = Color(0xFF353334),

            textAlign = TextAlign.Right,
        )
    )
}

@Composable
@Preview(showBackground = true)
fun MeetingAgendaCardViewPreview() {
    MeetingAgendaCardView(text = "1- يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، يمكن كتابة جدول الأعمال هنا، ")
}

