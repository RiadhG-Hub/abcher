package com.example.absher.services.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.absher.R

class MeetingsDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val meetingId = intent.getIntExtra("MEETING_ID", 0)
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            TaskScreen(meetingId)}
        }
    }
}

@Composable
fun TaskScreen(meetingId : Int? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)) // Light background color
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Handle back click */ }) {
                // Placeholder for back arrow icon
                Text("⬅", fontSize = 24.sp)
            }
            Text(
                text = "$meetingId",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(48.dp)) // Placeholder for icon space
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tabs Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TabButton(text = stringResource(R.string.meetings), isSelected = false)
            TabButton(text = "جدول الأعمال", isSelected = false)

            TabButton(text = "الحضور", isSelected = false)
            TabButton(text = "المرفقات", isSelected = true)

        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "#223323",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "الحالي - MTG-2024-84",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "تقدم 1",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "03:00",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "مهام الوصوليات",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "r-rr",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ممنوح",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    IconButton(onClick = { /* Handle edit */ }) {
                        // Placeholder for edit icon
                        Text("✏", fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "الحالة",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    IconButton(onClick = { /* Handle edit */ }) {
                        // Placeholder for edit icon
                        Text("✏", fontSize = 20.sp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "جانب الوصوليات المختارة بأيام العطلة الرسمية السنوية لعام 2024، المشمولة بالتأمينات، والتي يتم التسجيل لها في الدورة التدريبية العامة لعام 2024 بموجب التعليمات التنظيمية",
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Handle add click */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50) // Green button
                        )
                    ) {
                        Text(text = "أضافة تعليق", color = Color.White)
                    }
                    Button(
                        onClick = { /* Handle edit click */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50) // Green button
                        )
                    ) {
                        Text(text = "تعديل", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun TabButton(text: String, isSelected: Boolean) {
    Button(
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        onClick = { /* Handle tab click */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF4CAF50) else Color.Transparent
        ),
        modifier = Modifier

            .clip(RoundedCornerShape(0.dp))
    ) {

        Column (modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally){
            SvgIcon(R.drawable.meetingtabicon)
            Text(
                text = text,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 14.sp
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskScreenPreview() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    TaskScreen()}
}