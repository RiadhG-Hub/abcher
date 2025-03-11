package com.example.absher.services.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absher.services.data.models.Meeting

// Custom Arabic font (if available, e.g., "Noto Sans Arabic")
// Replace with your font resource if you have one


@Composable
fun MeetingCard(meeting: Meeting) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(12.dp) // Inner padding of the card
        ) {
            // Header: Status button and ID
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status button
                TextButton(
                    onClick = { /* Handle click */ },
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color(0xFFD4A017), // Gold color
                            shape = RoundedCornerShape(4.dp)
                        ),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFD4A017)
                    )
                ) {
                    Text(
                        text = meeting.statusName ?: "" ,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            // fontFamily = arabicFontFamily
                        )
                    )
                }

                // Meeting ID
                Text(
                    text = meeting.id.toString() ,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121), // Dark gray
                        // fontFamily = arabicFontFamily
                    )
                )
            }

            // Date and Reference Number
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange, // Calendar icon
                        contentDescription = "Date Icon",
                        tint = Color(0xFF757575), // Medium gray
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meeting.date ?: "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF757575),
                            // fontFamily = arabicFontFamily
                        )
                    )
                }

                Text(
                    text = meeting.location ?: "",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF757575),
                        // fontFamily = arabicFontFamily
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = meeting.notes ?: "",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF212121),
                    // fontFamily = arabicFontFamily
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Start and End Time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Start Time Icon",
                        tint = Color(0xFF4CAF50), // Green
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meeting.startTime ?: "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF212121),
                            // fontFamily = arabicFontFamily
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "End Time Icon",
                        tint = Color(0xFFF44336), // Red
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = meeting.endTime ?: "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF212121),
                            // fontFamily = arabicFontFamily
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Location
            Text(
                text = "المعرض التجاري",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color(0xFF757575),
                    // fontFamily = arabicFontFamily
                ),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MeetingCardPreview() {
//    // Set the direction to RTL for Arabic text
//    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
//        MeetingCard()
//    }
//}