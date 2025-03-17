package com.example.absher.services.view

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.absher.R
import com.example.absher.services.data.models.MeetingAttendee
import com.example.absher.services.data.models.MeetingInfoData
import com.example.absher.services.data.models.MeetingInfoResponse
import com.example.absher.services.helper.formatDateToArabic
import com.example.absher.services.helper.formatTimeToArabic
import com.example.absher.services.viewmodel.FetchMeetingInfoStateError
import com.example.absher.services.viewmodel.FetchMeetingInfoStateInit
import com.example.absher.services.viewmodel.FetchMeetingInfoStateLoading
import com.example.absher.services.viewmodel.FetchMeetingInfoStateSuccess
import com.example.absher.services.viewmodel.FetchMeetingInfoViewModel
import com.example.absher.services.viewmodel.MeetingDetailsNavigationViewModel
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.CustomTextStyles
import com.example.absher.ui.theme.Gray
import com.example.absher.ui.theme.SubtitleColor


@Composable
fun MeetingInfo(
    viewModel: MeetingDetailsNavigationViewModel = viewModel(),
    meetingId: Int = 0,
    fetchMeetingInfoViewModel: FetchMeetingInfoViewModel = viewModel()
) {
    val fetchMeetingInfo by fetchMeetingInfoViewModel.fetchMeetingState.observeAsState(
        FetchMeetingInfoStateInit()
    )

    when (fetchMeetingInfo) {
        is FetchMeetingInfoStateError -> Text("Error fetching meeting info", color = Color.Red)
        is FetchMeetingInfoStateInit -> Text("Initializing meeting info...")
        is FetchMeetingInfoStateLoading -> CircularProgressIndicator()
        is FetchMeetingInfoStateSuccess -> {
            val meetingData = (fetchMeetingInfo as FetchMeetingInfoStateSuccess).meetingAgenda
            if (meetingData != null) {
                MeetingDetailsCard(meetingData)
            } else {
                Text("Meeting data not available", color = Color.Gray)
            }
        }
    }
}


@Composable
private fun MeetingDetailsCard(meeting: MeetingInfoResponse, modifier: Modifier = Modifier) {

    val statusColor = when (meeting.data?.statusId) {
        1 -> Color(0xFF28A745) // Green for status 1
        2 -> Color(0xFFFFC107) // Yellow for status 2
        3 -> Color(0xFFDC3545) // Red for status 3
        else -> Color(0xFFD4A017) // Default color
    }

    val statusText = when (meeting.data?.statusId) {
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
                    text = "#${meeting.data?.id ?: "N/A"}", style = TextStyle(
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
                        text = stringResource(R.string.infonumber, meeting.data?.referenceNumber!!),
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
                        text = formatDateToArabic(meeting.data?.date ?: ""),
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
                Text(
                    text = meeting.data?.title ?: "No title available", style = TextStyle(
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
                    Text(
                        text = stringResource(
                            R.string.startmeeting, formatTimeToArabic(meeting.data?.startTime!!)
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
                    Text(
                        text = stringResource(
                            R.string.meetingend, formatTimeToArabic(meeting.data?.endTime!!)
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

                        Text(
                            text = formatDateToArabic(meeting.data?.date!!),
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

                        Text(
                            text = meeting.data?.location.toString(),
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

                        Text(
                            text = meeting.data?.committeeName.toString(),
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

                        Text(
                            text = meeting.data?.createdby.toString(),
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

            Text(
                text = meeting.data?.notes.toString(),

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
    val sampleMeeting = MeetingInfoResponse(
        success = true, message = "Meeting details fetched successfully", data = MeetingInfoData(
            id = 12345,
            referenceNumber = "REF-98765",
            committeeName = "Finance Committee",
            createdby = 101,
            statusId = 2,
            title = "Quarterly Financial Review",
            date = "2025-03-16",
            startTime = "10:00 AM",
            endTime = "11:00 AM",
            location = "Conference Room A",
            isCommittee = true,
            committeeId = 201,
            councilSessionId = 301,
            notes = "Discuss Q1 financials and projections for Q2",
            readOnly = false,
            meetingAttendees = listOf(
                MeetingAttendee(
                    userId = 1,
                    needsApproval = false,
                    jobTitle = "CEO",
                    name = "John Doe",
                    attended = true,
                    hasProfilePicture = true
                ), MeetingAttendee(
                    userId = 2,
                    needsApproval = true,
                    jobTitle = "CFO",
                    name = "Jane Smith",
                    attended = false,
                    hasProfilePicture = false
                )
            )
        )
    )
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MeetingDetailsCard(meeting = sampleMeeting)
        }
    }
}
