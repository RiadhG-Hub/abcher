package com.example.absher.services.view.meetings


import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.absher.R
import com.example.absher.services.view.meetings.pages.MeetingListPage
import com.example.absher.services.view.recommendations.pages.RecommendationsPage
import com.example.absher.services.viewmodel.meetings.HomeViewModel
import com.example.absher.ui.theme.AbsherTheme
import com.example.absher.ui.theme.GreenPrimary
import com.example.absher.ui.theme.SubtitleColor
import com.example.absher.ui.theme.White

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = viewModel()) {
    // Use the State directly from mutableStateOf
    val selectedNavItem by viewModel.selectedNavItem // No collectAsStateWithLifecycle needed

    Scaffold(
        topBar = {
            AbcherTopAppBar(
                title = stringResource(id = R.string.top_bar_title),
                navigationIcon = {
                    DefaultBackButton()
                })
        },

        bottomBar = {
            BottomNavigationBar(
                selectedNavItem = selectedNavItem, onNavItemSelected = { item ->
                    viewModel.selectNavItem(item)
                    navController.navigate(item) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardGrid()
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    )
}

@Composable
fun DefaultBackButton() {
    val context = LocalContext.current
    SvgIcon(drawable = R.drawable.right, modifier = Modifier
        .rotate(180f)
        .clickable {
            println("back button clicked")
            (context as? Activity)?.finish()
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbcherTopAppBar(
    title: String = "change me",
    actions: @Composable RowScope.() -> Unit = {},

    navigationIcon: @Composable () -> Unit = {},
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(
                color = Color(0xFFCDB372), shape = RoundedCornerShape(
                    bottomEnd = 16.dp, // Bottom-right corner
                    bottomStart = 16.dp // Bottom-left corner
                )
            ), contentAlignment = Alignment.TopCenter
    ) {
        Image(

            modifier = Modifier
                .size(width = 400.dp, height = 250.dp) // Set a larger size
                .offset(x = 0.dp, y = 20.dp),
            painter = painterResource(id = R.drawable.top_app_bar_background),
            contentDescription = "My SVG Icon"
        )
        TopAppBar(
            title = {
                Text(
                    text = title, style = MaterialTheme.typography.displayMedium
                )
            },
            actions = actions,
            navigationIcon = navigationIcon,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }

}

@Composable
fun SvgIcon(drawable: Int, modifier: Modifier = Modifier, defaultColor: Color? = null) {
    Box(modifier = modifier) {
        Image(

            colorFilter = if (defaultColor != null) {
                ColorFilter.tint(defaultColor)
            } else {
                null
            }, painter = painterResource(id = drawable), contentDescription = "My SVG Icon"
        )
    }
}

@Composable
fun CardGrid() {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardElement(
            title = stringResource(id = R.string.tasks),
            modifier = Modifier.weight(1f),
            onClick = {

            },
            subtitle = stringResource(id = R.string.tasks_desc),
            icon = R.drawable.task,
            titleColor = Color(color = 0XFFC3A355),
            backgroundIconColor = Color(0xFFFCFBF8),
        )



        CardElement(
            title = stringResource(id = R.string.files),
            modifier = Modifier.weight(1f),
            onClick = {
                val intent = Intent(context, RecommendationsPage::class.java)

                context.startActivity(intent)
            },
            subtitle = stringResource(id = R.string.files_desc),
            icon = R.drawable.note,
            titleColor = SubtitleColor,
            backgroundIconColor = Color(0xFFFCFBF8),
        )


    }
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {


        CardElement(
            title = stringResource(id = R.string.team),
            modifier = Modifier.weight(1f),
            onClick = {
                (context as? Activity)?.let {
                    val intent = Intent(it, MeetingListPage::class.java)
                    it.startActivity(intent)
                }
            },
            subtitle = stringResource(id = R.string.team_desc),
            icon = R.drawable.groups,
            titleColor = GreenPrimary,
            backgroundIconColor = GreenPrimary.copy(alpha = 0.1f),
        )

        CardElement(
            title = stringResource(id = R.string.notifications),
            modifier = Modifier.weight(1f),
            onClick = {
                (context as? Activity)?.let {
                    val intent = Intent(it, MeetingListPage::class.java)
                    it.startActivity(intent)
                }
            },
            subtitle = stringResource(id = R.string.notifications_desc),
            icon = R.drawable.online_prediction,
            titleColor = Color(color = 0XFFC3A355),
            backgroundIconColor = Color(0xFFFCFBF8),
        )


    }
}


@Composable
fun CardElement(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    title: String,
    subtitle: String,
    icon: Int,
    titleColor: Color,
    backgroundIconColor: Color
) {
    Card(
        modifier = modifier.clickable {
            onClick()
        },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start
        ) {
            SvgIcon(
                icon, modifier = Modifier
                    .border(
                        width = 0.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(8.dp) // Corner radius
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = backgroundIconColor)
                    .padding(7.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(color = titleColor)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Composable
fun BottomNavigationBar(selectedNavItem: String, onNavItemSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = White, contentColor = White, tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            icon = { SvgIcon(drawable = R.drawable.other_houses) },
            label = { Text("الرئيسية", style = MaterialTheme.typography.bodyMedium) },
            selected = selectedNavItem == "home",
            onClick = { onNavItemSelected("home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),


            )
        )
        NavigationBarItem(
            icon = { SvgIcon(drawable = R.drawable.absher) },
            label = { Text("مجتمع الوزارة", style = MaterialTheme.typography.bodyMedium) },
            selected = selectedNavItem == "chat",
            onClick = { onNavItemSelected("chat") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

            )
        )
        NavigationBarItem(
            icon = { SvgIcon(drawable = R.drawable.apps) },
            label = { Text("الخدمات", style = MaterialTheme.typography.bodyMedium) },
            selected = selectedNavItem == "more",
            onClick = { onNavItemSelected("more") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

            )
        )
        NavigationBarItem(
            icon = { SvgIcon(drawable = R.drawable.textsms) },
            label = { Text("المحادثات", style = MaterialTheme.typography.bodyMedium) },
            selected = selectedNavItem == "settings",
            onClick = { onNavItemSelected("settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

            )
        )
        NavigationBarItem(
            icon = { SvgIcon(drawable = R.drawable.wallet) },
            label = { Text("وثائقى", style = MaterialTheme.typography.bodyMedium) },
            selected = selectedNavItem == "profile",
            onClick = { onNavItemSelected("profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),

                indicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,

    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    name = "homeScreenPreview"
)
@Composable
fun HomeScreenPreview() {

    AbsherTheme {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            HomeScreen(
                navController = rememberNavController(), // Mock NavHostController
                viewModel = HomeViewModel() // Instantiate a new ViewModel for preview
            )
        }
    }
}