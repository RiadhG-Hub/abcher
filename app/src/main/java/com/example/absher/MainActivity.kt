package com.example.absher

import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.absher.services.adapter.MeetingApiAdapter
import com.example.absher.services.view.MeetingListScreen
import com.example.absher.ui.navigation.AppNavigation
import com.example.absher.ui.theme.MyAppTheme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.concurrent.Task
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

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
                    AppNavigation()                }
            }
        }
    }
}





