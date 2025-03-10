package com.example.absher.ui.screens


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.*
import com.example.absher.ui.screens.ui.theme.AbsherTheme
import com.example.absher.ui.viewmodel.FetchMeetingStateError
import com.example.absher.ui.viewmodel.FetchMeetingStateLoading
import com.example.absher.ui.viewmodel.FetchMeetingStateSuccess
import com.example.absher.ui.viewmodel.MeetingViewModel


class MeetingActivity : ComponentActivity() {
    private lateinit var viewModel: MeetingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
viewModel = ViewModelProvider(this)[MeetingViewModel::class.java]
        viewModel.fetchMeetings()
        setContent {
            AbsherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MeetingListScreen(

                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }


    }
}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun MeetingListScreen(viewModel: MeetingViewModel, modifier: Modifier = Modifier) {
    val meetingsState = viewModel.meetingsState.observeAsState() // State<FetchMeetingState?>

// Access the value explicitly
    val stateValue = meetingsState.value

    when (stateValue) {
        is FetchMeetingStateLoading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
        }

        is FetchMeetingStateSuccess -> {
            val meetings = (meetingsState as FetchMeetingStateSuccess).meetings
           Text(text = meetings.first().id.toString())
        }

        is FetchMeetingStateError -> {
            val message = (meetingsState as FetchMeetingStateError).error
            ErrorView(message)
        }

        else -> {
            Text("No meetings found", modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))
        }
    }
}





@Composable
fun ErrorView(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message, color = Color.Red)
        Button(onClick = { /* Retry fetching meetings */ }) {
            Text("Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AbsherTheme {
        MeetingListScreen(viewModel = MeetingViewModel())    }
}