package com.example.absher.services.view.meetings

// Custom Arabic font (if available, e.g., "Noto Sans Arabic")
// Replace with your font resource if you have one


import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.R
import com.example.absher.services.data.models.meetings.Meeting
import com.example.absher.services.helper.formatDateToArabic
import com.example.absher.services.helper.formatTimeToArabic
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.GreenPrimary
import com.example.absher.ui.theme.statusDefaultColor
import com.example.absher.ui.theme.statusGreenColor
import com.example.absher.ui.theme.statusRedColor
import com.example.absher.ui.theme.statusYellowColor


@Composable
fun ClickableUnderlinedText(onClick: () -> Unit) {
    Text(
        text = stringResource(R.string.moredetails),
        style = MaterialTheme.typography.labelSmall,
        color = GreenPrimary,
        textAlign = TextAlign.End,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Log.i("ClickableUnderlinedText", "Clicked")
                onClick()
            }
            .padding(4.dp)
    )
}

@Composable
fun MeetingCard(meeting: Meeting, onClick: () -> Unit) {
    val statusColor = when (meeting.statusId) {
        1 -> statusGreenColor()
        2 -> statusYellowColor()
        3 -> statusRedColor()
        else -> statusDefaultColor()
    }

    val statusText = when (meeting.statusId) {
        1 -> stringResource(R.string.status_approved)
        2 -> stringResource(R.string.status_pending)
        3 -> stringResource(R.string.status_rejected)
        else -> stringResource(R.string.status_na)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = " ${meeting.id}#",
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small
                        )
                        .border(1.dp, statusColor, MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelLarge,
                        color = statusColor
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small
                        )
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            MaterialTheme.shapes.small
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.infonumber, meeting.referenceNumber!!),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // SvgIcon(R.drawable.calendar_today)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDateToArabic(meeting.date ?: ""),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = meeting.notes ?: "لا توجد ملاحظات ",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgIcon(R.drawable.timer)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(
                            R.string.startmeeting,
                           )
                        ,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text =
                            formatTimeToArabic(meeting.startTime!!),

                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgIcon(R.drawable.timer_off)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(
                            R.string.meetingend,
                            formatTimeToArabic(meeting.endTime!!)
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text =
                            formatTimeToArabic(meeting.endTime),

                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f),
                    thickness = 1.dp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.moredetails),
                style = MaterialTheme.typography.labelMedium.copy(GreenPrimary),

                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .padding(4.dp)
            )
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
                Log.e("MeetingCard ", "MeetingCardPreview: ${sampleMeeting.title}")
            })
        }
    }
}