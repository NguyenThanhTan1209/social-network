package com.example.socialnetworkapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.socialnetworkapp.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val screenItems = listOf(
        BottomNavItem(
            "Newsfeed",
            painterResource(R.drawable.newsfeed_unselected),
            painterResource(R.drawable.newsfeed_selected)
        ),
        BottomNavItem(
            "Search",
            painterResource(R.drawable.search_unselected),
            painterResource(R.drawable.search_selected)
        ),
        BottomNavItem(
            "Search",
            painterResource(R.drawable.add_post_unselected),
            painterResource(R.drawable.add_post_unselected)
        ),
        BottomNavItem(
            "Notification",
            painterResource(R.drawable.notification_unselected),
            painterResource(R.drawable.notification_selected)
        ),
        BottomNavItem(
            "Profile",
            painterResource(R.drawable.profile_unselected),
            painterResource(R.drawable.profile_selected)
        ),
    )
    var currentIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.secondary,
            ) {
                screenItems.forEachIndexed { index, screenItem ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = if (currentIndex == index) screenItem.iconSelected else screenItem.iconUnselected,
                                contentDescription = screenItem.title,
                                tint = if (currentIndex == index) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondary.copy(
                                    alpha = 0.6f
                                )
                            )
                        },
                        selected = index == currentIndex,
                        onClick = { currentIndex = index },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
                            selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.6f),
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
        },
    ) { it ->
        Content(index = currentIndex, paddingValues = it)
    }
}

@Composable
fun Content(index: Int, paddingValues: PaddingValues) {
    when (index) {
        0 -> NewsfeedScreen(paddingValues = paddingValues)
        1 -> SearchScreen(paddingValues = paddingValues)
        2 -> AddPostScreen(paddingValues = paddingValues)
        3 -> NotificationScreen(paddingValues = paddingValues)
        4 -> ProfileScreen(paddingValues = paddingValues)
    }
}

data class BottomNavItem(
    val title: String,
    val iconUnselected: Painter,
    val iconSelected: Painter,
)