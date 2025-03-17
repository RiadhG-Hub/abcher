package com.example.absher.services.view.recommendations.pages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.absher.R
import com.example.absher.services.view.meetings.AbcherTopAppBar
import com.example.absher.services.view.meetings.DefaultBackButton
import com.example.absher.services.view.meetings.SvgIcon
import com.example.absher.services.view.recommendations.RecommendationsSearchCard
import com.example.absher.ui.theme.AbsherTheme

class RecommendationsPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AbsherTheme {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    topBar = {
                        AbcherTopAppBar(
                            title = "التوصيات",
                            navigationIcon = {
                                DefaultBackButton()
                            },
                            actions = {
                                SvgIcon(
                                    R.drawable.notifications_active,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            })
                    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (modifier = Modifier.padding(innerPadding)){
                        RecommendationsSearchCard()
                    }
                }
            }}
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AbsherTheme {
        Greeting("Android")
    }
}