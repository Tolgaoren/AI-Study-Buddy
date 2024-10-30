package com.toren.hackathon24educationproject.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreens(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object ChooseSubject : BottomBarScreens(
        route = "Choose Subject",
        title = "Choose Subject",
        icon = Icons.Outlined.PlayArrow,
        selectedIcon = Icons.Filled.PlayArrow
    )

    object Classroom : BottomBarScreens(
        route = "Classroom",
        title = "Classroom",
        icon = Icons.Outlined.Menu,
        selectedIcon = Icons.Filled.Menu
    )

    object Profile : BottomBarScreens(
        route = "Profile",
        title = "Profile",
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person

    )
}