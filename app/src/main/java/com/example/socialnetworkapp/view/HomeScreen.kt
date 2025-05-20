package com.example.socialnetworkapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val screenItems = listOf(
        BottomNavItem("Newsfeed", Icons.Filled.Home),
        BottomNavItem("Search", Icons.Filled.Search),
        BottomNavItem("Notification", Icons.Filled.Notifications),
        BottomNavItem("Profile", Icons.Filled.Person),
    )
    var currentIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar(

            ) {
                screenItems.forEachIndexed { index, screenItems ->
                    NavigationBarItem(
                        icon = { Icon(screenItems.icon, contentDescription = screenItems.title) },
                        label = { Text(screenItems.title) },
                        selected = index == currentIndex,
                        onClick = { currentIndex = index }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Thêm mới */ },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm")

            }
        },
        floatingActionButtonPosition = FabPosition.End,
        ) { it ->
        Content(index = currentIndex, paddingValues = it)
    }
}

@Composable
fun Content(index:Int, paddingValues: PaddingValues) {
    when (index) {
        0 -> NewsfeedScreen(paddingValues = paddingValues)
        1 -> SearchScreen(paddingValues = paddingValues)
        2 -> NotificationScreen(paddingValues = paddingValues)
        3 -> ProfileScreen(paddingValues = paddingValues)
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)