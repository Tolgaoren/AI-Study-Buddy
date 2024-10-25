package com.toren.hackathon24educationproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toren.hackathon24educationproject.presentation.classroom.Classroom
import com.toren.hackathon24educationproject.presentation.create_classroom.CreateClassroom
import com.toren.hackathon24educationproject.presentation.join_classroom.JoinClassroom
import com.toren.hackathon24educationproject.presentation.practice.Practice
import com.toren.hackathon24educationproject.presentation.profile.Profile
import com.toren.hackathon24educationproject.presentation.sign_in.SignIn
import com.toren.hackathon24educationproject.presentation.sign_up.SignUp

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.SignIn.route)
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
        composable(route = Screens.SignIn.route) {
            SignIn()
        }
        composable(route = Screens.SignUp.route) {
            SignUp()
        }
        composable(route = Screens.Practice.route) {
            Practice()
        }
        composable(route = Screens.Profile.route) {
            Profile()
        }
        composable(route = Screens.JoinClassroom.route) {
            JoinClassroom()
        }
        composable(route = Screens.CreateClassroom.route) {
            CreateClassroom()
        }
    }
}