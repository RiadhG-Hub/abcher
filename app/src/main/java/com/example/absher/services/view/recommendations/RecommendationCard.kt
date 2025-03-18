package com.example.absher.services.view.recommendations

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.R
import com.example.absher.services.data.models.recommendations.Recommendation
import com.example.absher.services.helper.formatDateToArabic
import com.example.absher.services.helper.formatTimeToArabic
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.BackgroundGray
import com.example.absher.ui.theme.DarkGray
import com.example.absher.ui.theme.GreenPrimary
import com.example.absher.ui.theme.LightGray
import com.example.absher.ui.theme.MediumGray
import com.example.absher.ui.theme.StatusDefault
import com.example.absher.ui.theme.StatusGreen
import com.example.absher.ui.theme.StatusRed
import com.example.absher.ui.theme.StatusYellow


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
fun RecommendationCard(recommendation : Recommendation, onClick: () -> Unit) {
    val statusColor = when (recommendation.statusId) {
        1 -> StatusGreen
        2 -> StatusYellow
        3 -> StatusRed
        else -> StatusDefault
    }

    val statusText = when (recommendation.statusId) {
        1 -> "Approved"
        2 -> "Pending"
        3 -> "Rejected"
        else -> "N/A"
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
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = " ${recommendation.id}#",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = Color(0x00E0F7FA), shape = MaterialTheme.shapes.small)
                        .border(1.dp, statusColor, MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelMedium,
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
                        .background(color = Color(0x00E0F7FA), shape = MaterialTheme.shapes.small)
                        .border(1.dp, MediumGray, MaterialTheme.shapes.small)
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.infonumber, recommendation.meetingReferenceNo!!),
                        style = MaterialTheme.typography.bodySmall,
                        color = MediumGray
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // SvgIcon(R.drawable.calendar_today) // Uncomment if you have this
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDateToArabic(recommendation.dueDate),
                        style = MaterialTheme.typography.bodyMedium,
                        color = LightGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(color = BackgroundGray)
                    .padding(12.dp)
            ) {
                Text(
                    text = recommendation.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkGray,
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
                    SvgIcon(R.drawable.timer) // Uncomment if you have this
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(
                            R.string.startmeeting,
                            formatTimeToArabic("13:00")
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = BackgroundGray,
                    thickness = 1.dp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgIcon(R.drawable.timer_off) // Uncomment if you have this
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(
                            R.string.meetingend,
                            formatTimeToArabic("17:00")
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = BackgroundGray,
                    thickness = 1.dp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            CustomProgressBar(
                progress = recommendation.percentage.toFloat(), height = 16.dp
            )
            Spacer(modifier = Modifier.height(12.dp))
            ClickableUnderlinedText { onClick() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MeetingCardPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
           val recommendation = Recommendation(
                id = 1,
                createdBy = 101,
                dueDate = "2025-03-30",
                createdAt = "2025-03-15",
                meetingAgendaId = 10,
                text = "Improve project delivery efficiency",
                statusId = 2,
                createdByName = "John Doe",
                owner = 102,
                ownerName = "Jane Smith",
                percentage = 85,
                status = "In Progress",
                meetingId = 5,
                meetingReferenceNo = "MTG-2025-01",
                committeeName = "Project Oversight Committee",
                recommendationName = "Efficiency Improvement Plan",
                canEdit = true
            )
            RecommendationCard(recommendation = recommendation, onClick = {
                Log.e("MeetingCard ", "MeetingCardPreview: ${recommendation.meetingId}")
            })
        }
    }
}