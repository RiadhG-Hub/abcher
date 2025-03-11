package com.example.absher.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.absher.services.view.MeetingListScreen






@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { MeetingListScreen() }
        composable("chat") { ChatScreen() }
        composable("more") { MoreScreen() }
        composable("settings") { SettingsScreen() }
        composable("profile") { ProfileScreen() }
    }
}

@Composable
fun ProfileScreen() {
    Text(text = "Profile Screen")
}

@Composable
fun SettingsScreen() {
    Text(text = "Settings Screen")
}

@Composable
fun MoreScreen() {
    Text(text = "More Screen")
}

@Composable
fun ChatScreen() {
    Text(text = "Chat Screen")
}