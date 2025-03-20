package com.example.absher.services.view.recommendations

import AbsherDatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.services.data.models.meetings.MeetingRequestBody
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.BackgroundGray
import com.example.absher.ui.theme.CustomTextStyles
import com.example.absher.ui.theme.MediumGray
import com.example.absher.ui.theme.OutlinedButtonColor
import com.example.absher.ui.theme.SubtitleColor


@Composable
fun RecommendationsSearchCard(

    onSearch: (MeetingRequestBody) -> Unit
) {
    var startDate by remember { mutableStateOf<String?>(null) }
    var endDate by remember { mutableStateOf<String?>(null) }

    var jobTitle by remember { mutableStateOf<String?>(null) }
    var idNumber by remember { mutableStateOf<String?>(null) }

    // Add a key to force content recomposition
    var contentKey by remember { mutableStateOf(0) }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 12.dp)
                .border(1.dp, BackgroundGray, MaterialTheme.shapes.small),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {

                Row(
                    modifier = Modifier.padding(end = 8.dp, start = 8.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)) {
                        Text(
                            text = "البحث بإسم المهمة",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SubtitleColor,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.small
                                )
                                .border(1.dp, MediumGray, MaterialTheme.shapes.small),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomTextField(
                                modifier = Modifier.padding(
                                    end = 8.dp,
                                    start = 8.dp,
                                    top = 4.dp,
                                    bottom = 3.dp
                                ), placeholderText = "إسم المهمة", onValueChange = {
                                        newText -> jobTitle = newText
                                }
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "البحث بتاريخ البداية",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SubtitleColor,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        AbsherDatePicker(
                            onDateSelected = { selectedDate ->
                                startDate = selectedDate // Update parent state
                            }
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "البحث برقم التعريف",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SubtitleColor,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Box(
                            modifier = Modifier
                                .padding(0.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = MaterialTheme.shapes.small
                                )
                                .border(1.dp, MediumGray, MaterialTheme.shapes.small),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomTextField(
                                modifier = Modifier.padding(
                                    end = 8.dp,
                                    start = 8.dp,
                                    top = 4.dp,
                                    bottom = 3.dp
                                ), placeholderText = "رقم التعريف", onValueChange = {
                                        newText -> idNumber = newText
                                }
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = "البحث بتاريخ النهاية",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = SubtitleColor,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        AbsherDatePicker(
                            onDateSelected = { selectedDate ->
                                endDate = selectedDate // Update parent state
                            }
                        )
                    }
                }
                HorizontalDivider()
                OutlinedButton(
                    onClick = {
                        // Trigger the search action
                        onSearch(MeetingRequestBody(startDate, endDate, jobTitle, idNumber))

                        // Update contentKey to trigger a recomposition of the content
                        contentKey += 1
                    },
                    modifier = Modifier
                        .padding(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = OutlinedButtonColor // Text/icon color
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = OutlinedButtonColor // Custom border color
                    ),
                    shape = RoundedCornerShape(4.dp) // Custom shape
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("بحث", style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ))
                    }
                }
            }
        }

        // Pass the contentKey to force a recomposition


}

@Preview(showBackground = true)
@Composable
fun RecommendationsSearchCardPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            RecommendationsSearchCard(onSearch = { searchValue ->
                println(searchValue)
            })
        }
    }
}