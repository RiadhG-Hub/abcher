package com.example.absher.services.view.recommendations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.absher.R
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoData
import com.example.absher.services.data.models.recommendations.FetchRecommendationInfoResponse
import com.example.absher.services.helper.formatDateToArabic
import com.example.absher.services.helper.formatTimeToArabic
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.services.viewmodel.meetings.MeetingDetailsNavigationViewModel
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationInfoStateError
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationInfoStateInit
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationInfoStateLoading
import com.example.absher.services.viewmodel.recommendations.FetchRecommendationInfoStateSuccess
import com.example.absher.services.viewmodel.recommendations.RecommendationViewModel
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.CustomTextStyles
import com.example.absher.ui.theme.Gray
import com.example.absher.ui.theme.SubtitleColor

@Composable
fun RecommendationInfo(
    viewModel: MeetingDetailsNavigationViewModel = hiltViewModel(),
    meetingId: Int = 0,
    recommendationViewModel: RecommendationViewModel = hiltViewModel()
) {
    val fetchMeetingInfo by recommendationViewModel.fetchRecommendationInfoState.observeAsState(
        FetchRecommendationInfoStateInit()
    )

    when (fetchMeetingInfo) {
        is FetchRecommendationInfoStateError -> Text("Error fetching meeting info", color = Color.Red)
        is FetchRecommendationInfoStateInit -> Text("Initializing meeting info...")
        is FetchRecommendationInfoStateLoading -> CircularProgressIndicator()
        is FetchRecommendationInfoStateSuccess -> {
            val recommendation = (fetchMeetingInfo as FetchRecommendationInfoStateSuccess).recommendation
            RecommendationInfoCard(recommendation)
        }
    }
}

@Composable
private fun RecommendationInfoCard(recommendation: FetchRecommendationInfoData, modifier: Modifier = Modifier) {

    val statusColor = when (recommendation.statusId) {
        1 -> Color(0xFF28A745) // Green for status 1
        2 -> Color(0xFFFFC107) // Yellow for status 2
        3 -> Color(0xFFDC3545) // Red for status 3
        else -> Color(0xFFD4A017) // Default color
    }

    val statusText = when (recommendation.statusId) {
        1 -> "Approved"   // Text for status 1
        2 -> "Pending"    // Text for status 2
        3 -> "Rejected"   // Text for status 3
        else -> "N/A"     // Default text
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${recommendation.id}", style = TextStyle(
                        fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF212121)
                    )
                )

                Box(
                    modifier = Modifier

                        .padding(8.dp)
                        .background(color = Color(0x00E0F7FA), shape = RoundedCornerShape(8.dp))
                        .border(1.dp, statusColor, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = statusText, style = TextStyle(
                            fontSize = 10.sp,
                            lineHeight = 16.sp,

                            fontWeight = FontWeight(700),

                            color = statusColor,
                            // fontFamily = arabicFontFamily
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier

                        .padding(8.dp)
                        .background(color = Color(0x00E0F7FA), shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color(0xFFCBCBCB), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.infonumber, recommendation.id),
                        style = TextStyle(
                            fontSize = 10.sp,
                            lineHeight = 16.sp,

                            fontWeight = FontWeight(700),
                            color = Color(0xFF5A5A5A),

                            )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgIcon(R.drawable.calendar_today)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = formatDateToArabic(recommendation.id.toString() ?: ""),
                        style = TextStyle(fontSize = 12.sp, color = Color(0xFF757575))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF2F2F2))
                    .padding(12.dp)
            ) {
                //todo check this entity title
                Text(
                    text = recommendation.id.toString() , style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(700),
                        color = Color(0xFF353334),

                        textAlign = TextAlign.Right,
                    ), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth()
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
                    //todo check this entity title
                    Text(
                        text = stringResource(
                            R.string.startmeeting, formatTimeToArabic(recommendation.id.toString())
                        ), style = TextStyle(fontSize = 12.sp, color = Color(0xFF212121))
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .background(Color(0XFFF2F2F2)),
                    thickness = 1.dp
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SvgIcon(R.drawable.timer_off)
                    Spacer(modifier = Modifier.width(4.dp))
                    //todo check this entity endTime
                    Text(
                        text = stringResource(
                            R.string.meetingend, formatTimeToArabic(recommendation.id.toString())
                        ), style = TextStyle(fontSize = 12.sp, color = Color(0xFF212121))
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .background(Color(0XFFF2F2F2)),
                    thickness = 1.dp
                )
            }

            Row {
                Column {
                    Row {
                        SvgIcon(
                            R.drawable.calendar_today,
                            defaultColor = Gray,
                            modifier = modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "تاريخ الإجتماع",
                            // Small/Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(400),
                                color = Color(0xFF353334),

                                textAlign = TextAlign.Right,
                            )
                        )
//todo check this entity date
                        Text(
                            text = formatDateToArabic(recommendation.id.toString()),
                            modifier = modifier.padding(start = 62.dp),

                            // Small/Bold
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(700),
                                color = Color(0xFF353334),
                                textAlign = TextAlign.Right,
                            )
                        )

                    }
                    HorizontalDivider()
                    Row {
                        SvgIcon(
                            R.drawable.meeting_room,
                            defaultColor = Gray,
                            modifier = modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "قاعة / غرفة  الإجتماعات",
                            // Small/Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(400),
                                color = Color(0xFF353334),

                                textAlign = TextAlign.Right,
                            )
                        )
//todo check this entity location
                        Text(
                            text = recommendation.id.toString(),
                            modifier = modifier.padding(start = 18.dp),
                            // Small/Bold
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(700),
                                color = Color(0xFF353334),
                                textAlign = TextAlign.Right,
                            )
                        )

                    }
                    HorizontalDivider()
                    Row {
                        SvgIcon(
                            R.drawable.house_info,
                            defaultColor = Gray,
                            modifier = modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "إسم المجلس/ اللجنة",
                            // Small/Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(400),
                                color = Color(0xFF353334),

                                textAlign = TextAlign.Right,
                            )
                        )
//todo check this entity committeeName
                        Text(
                            text = recommendation.id.toString(),
                            modifier = modifier.padding(start = 36.dp),
                            // Small/Bold
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(700),
                                color = Color(0xFF353334),
                                textAlign = TextAlign.Right,
                            )
                        )

                    }
                    HorizontalDivider()
                    Row {
                        SvgIcon(
                            R.drawable.person_outline,
                            defaultColor = Gray,
                            modifier = modifier.padding(end = 4.dp)
                        )
                        Text(
                            text = "تم انشاؤه بواسطة",
                            // Small/Regular
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(400),
                                color = Color(0xFF353334),

                                textAlign = TextAlign.Right,
                            )
                        )
//todo check this entity createdBy
                        Text(
                            text = recommendation.id.toString(),
                            modifier = modifier.padding(start = 49.dp),

                            // Small/Bold
                            style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 20.sp,

                                fontWeight = FontWeight(700),
                                color = Color(0xFF353334),
                                textAlign = TextAlign.Right,
                            )
                        )

                    }
                    HorizontalDivider()
                }

            }
            Row(modifier = modifier.padding(top = 8.dp)) {

                SvgIcon(
                    drawable = R.drawable.timer,
                    defaultColor = Color(0XFF808080),
                    modifier = modifier.padding(end = 4.dp)
                )
                Text(
                    text = "ملاحظات",

                    // Small/Regular
                    style = CustomTextStyles.SmallRegular12
                )
            }
//todo check this entity notes
            Text(
                text = recommendation.id.toString(),

                // Small/Bold
                style = CustomTextStyles.SmallBold.copy(color = SubtitleColor)
            )

            Spacer(modifier = Modifier.height(12.dp))


        }
    }
}


@Preview(showBackground = true)
@Composable
fun MeetingDetailsCardPreview() {

    val response = FetchRecommendationInfoResponse(
        data = FetchRecommendationInfoData(
            id = 1,
            createdBy = 101,
            createdAt = "2024-03-19T10:00:00Z",
            dueDate = "2024-04-01T23:59:59Z",
            meetingAgendaId = 55,
            text = "Enhance project efficiency by automating daily reports.",
            statusId = 2,
            createdByName = "John Doe",
            owner = 102,
            ownerName = "Jane Smith",
            recommendationTypeId = 3,
            recommendationTypeName = "Process Improvement",
            percentage = 75,
            status = "In Progress"
        ),
        success = true,
        message = "Recommendation info fetched successfully."
    )

    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            RecommendationInfoCard(response.data)
        }
    }
}
