import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.R
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.CustomTextStyles
import com.example.absher.ui.theme.MediumGray
import com.example.absher.ui.theme.SubtitleColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Child Composable with callback
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbsherDatePicker(
    onDateSelected: (String) -> Unit, // Callback to send date to parent
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()

    Column {

        Box(
            modifier = Modifier
                .padding(0.dp)
                .background(color = Color.Transparent, shape = MaterialTheme.shapes.small)
                .border(1.dp, MediumGray, MaterialTheme.shapes.small)
                .clickable{
                    showDialog = true
                } ,
        ){
        Row(modifier = Modifier.fillMaxWidth().padding(end = 8.dp, start = 8.dp, top = 8.dp, bottom = 8.dp),horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = selectedDate,
                style = MaterialTheme.typography.bodyMedium.copy(color = SubtitleColor)
            )
            SvgIcon(R.drawable.date_range)
        }
        }



        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val localDate = Instant.ofEpochMilli(millis)
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate()
                                val formattedDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                selectedDate = formattedDate
                                onDateSelected(formattedDate) // Send date to parent
                            }
                            showDialog = false
                        }
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

// Parent Composable
@Composable
fun ParentScreenExample() {
    // State in the parent to hold the selected date
    var dateFromPicker by remember { mutableStateOf<String?>(null) }

    Column {
        // Display the date received from the child
        Text(
            text = dateFromPicker?.let { "Date from picker: $it" } ?: "No date selected yet"
        )

        // Child composable with callback
       AbsherDatePicker(
            onDateSelected = { selectedDate ->
                dateFromPicker = selectedDate // Update parent state
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AbsherDatePickerPreview() {
    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
    AbsherDatePicker(onDateSelected = {})


        }}
}