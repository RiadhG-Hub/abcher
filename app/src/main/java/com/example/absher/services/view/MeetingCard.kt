package com.example.absher.services.view

import android.util.Log
import android.webkit.ConsoleMessage.MessageLevel.LOG
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absher.R
import com.example.absher.services.data.models.Meeting
import com.example.absher.services.view.ui.theme.AbsherTheme

// Custom Arabic font (if available, e.g., "Noto Sans Arabic")
// Replace with your font resource if you have one



@Composable
fun ClickableUnderlinedText(onClick: () -> Unit) {
    Text(
        text = stringResource(R.string.moredetails),
        style = TextStyle(
            fontSize = 12.sp,
            color = Color(0xFF39836B),
            textDecoration = TextDecoration.Underline // Adds underline
        ),
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.i(
                    "ClickableUnderlinedText",
                    "ClickableUnderlinedText: ClickableUnderlinedText"
                )
                onClick()
            } // Makes the text clickable
            .padding(4.dp) // Adds slight padding for better tap experience
    )
}

@Composable
fun MeetingCard(meeting: Meeting,  onClick: () -> Unit) {
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


                // Meeting ID
                Text(
                    text = " ${meeting.id}#" ,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF212121), // Dark gray
                        // fontFamily = arabicFontFamily
                    )
                )

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
            }

            // Date and Reference Number
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {


                Box(modifier = Modifier

                    .padding(8.dp)
                    .background(color = Color(0x00E0F7FA), shape = RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFCBCBCB), RoundedCornerShape(8.dp))
                    .padding(12.dp)){
                    Text(
                        text = stringResource(R.string.infonumber, meeting.referenceNumber!!),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF757575),
                            // fontFamily = arabicFontFamily
                        )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SvgIcon(R.drawable.calendar_today)
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
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = Color(0xFFF2F2F2))

                    .padding(12.dp)
            ) {
                Text(
                    text = meeting.notes ?: "",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF353334),
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Start and End Time
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SvgIcon(R.drawable.timer)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.startmeeting, meeting.startTime!!),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color(0xFF212121),
                            // fontFamily = arabicFontFamily
                        )
                    )
                }
                HorizontalDivider(modifier = Modifier
                    .padding(vertical = 4.dp)
                    .background(Color(0XFFF2F2F2)), thickness = 1.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SvgIcon(R.drawable.timer_off)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.meetingend, meeting.endTime!!),
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
            ClickableUnderlinedText{
onClick()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeetingCardPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            val sampleMeeting = Meeting(
                id = 7,
                statusId = 5,
                referenceNumber = "MTG-2024-0",
                title = "إجتماع مناقشة نظام إدارة الجلسات الخاصة بالمجالس واللجان",
                date = "2024-07-01T00:00:00",
                startTime = "13:00",
                endTime = "17:00",
                location = "Intalio Meeting Room",
                isCommittee = false,
                notes = "جناح الديوان العام للمحاسبة بمؤتمر \"ليب 2024\" يشهد إقبالاً واسعاً للاطلاع على تجربة المراجعة الإلكترونية\n" +
                        "جناح الديوان العام للمحاسبة بمؤتمر \"ليب 2024\" يشهد إقبالاً واسعاً للاطلاع على تجربة المراجعة الإلكترونية\n" +
                        "جناح الديوان العام للمحاسبة بمؤتمر \"ليب 2024\" يشهد إقبالاً واسعاً للاطلاع على تجربة المراجعة الإلكترونية",
                committeeName = "لجان النظر في المخالفات والتظلمات",
                statusName = "تم الانتهاء"
            )
            MeetingCard(meeting = sampleMeeting, onClick = {
                Log.e("MeetingCard ", "MeetingCardPreview: ${sampleMeeting.title}", )
            })
        }
    }
}