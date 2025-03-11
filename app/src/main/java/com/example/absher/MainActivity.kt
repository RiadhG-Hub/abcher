package com.example.absher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.absher.services.view.HomeScreen
import com.example.absher.ui.theme.MyAppTheme
import com.example.absher.services.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint  // ✅ REQUIRED for Hilt to work in Activities
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Correct way to set RTL layout direction
        val locale = Locale("ar")
        val config = resources.configuration.apply {
            setLocale(locale)
            setLayoutDirection(locale)
        }
        //val context = createConfigurationContext(config)


        setContent {
            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        navController = rememberNavController(), // Mock NavHostController
                        viewModel = HomeViewModel() // Instantiate a new ViewModel for preview
                    )

                }
            }
        }
    }}





