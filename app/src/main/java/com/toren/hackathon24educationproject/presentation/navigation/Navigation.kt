package com.toren.hackathon24educationproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toren.hackathon24educationproject.presentation.classroom.Classroom
import com.toren.hackathon24educationproject.presentation.practice.Practice
import com.toren.hackathon24educationproject.presentation.profile.Profile

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreens.Classroom.route)
    {
        composable(route = BottomBarScreens.Classroom.route) {
            Classroom()
        }
        composable(route = BottomBarScreens.Practice.route) {
            Practice()
        }
        composable(route = BottomBarScreens.Profile.route) {
            Profile()

        }
    }
}