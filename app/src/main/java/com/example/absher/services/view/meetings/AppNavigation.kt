package com.example.absher.services.view.meetings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.absher.R
import com.example.absher.ui.theme.ThemeViewModel

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController = navController) }
        composable("chat") { ChatScreen() }
        composable("more") { MoreScreen() }
        composable("settings") { SettingsScreen(themeViewModel = themeViewModel) }
        composable("profile") { ProfileScreen() }
    }
}

@Composable
fun ProfileScreen() {
    Text(text = "Profile Screen")
}

@Composable
fun SettingsScreen(themeViewModel: ThemeViewModel) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.dark_mode),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { themeViewModel.toggleTheme() }
            )
        }
    }
}

@Composable
fun MoreScreen() {
    Text(text = "More Screen")
}

@Composable
fun ChatScreen() {
    Text(text = "Chat Screen")
}