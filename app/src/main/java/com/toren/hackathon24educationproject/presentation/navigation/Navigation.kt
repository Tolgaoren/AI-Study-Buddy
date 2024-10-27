package com.toren.hackathon24educationproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.toren.hackathon24educationproject.presentation.classroom.ClassroomScreen
import com.toren.hackathon24educationproject.presentation.create_classroom.CreateClassroomScreen
import com.toren.hackathon24educationproject.presentation.practice.PracticeScreen
import com.toren.hackathon24educationproject.presentation.profile.ProfileScreen
import com.toren.hackathon24educationproject.presentation.sign_in.SignInScreen
import com.toren.hackathon24educationproject.presentation.sign_in.SignInViewModel
import com.toren.hackathon24educationproject.presentation.sign_up.SignUpScreen
import com.toren.hackathon24educationproject.presentation.sign_up.SignUpViewModel

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
            ClassroomScreen()
        }
        composable(route = BottomBarScreens.Practice.route) {
            PracticeScreen()
        }
        composable(route = BottomBarScreens.Profile.route) {
            ProfileScreen()
        }
        composable(route = Screens.SignIn.route) {
            val viewModel: SignInViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignInScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent,
                onNavigateToSignUp = {
                    navController.navigate(Screens.SignUp.route)
                },
                onNavigateToClassroom = {
                    navController.navigate(BottomBarScreens.Classroom.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToCreateClassroom = {
                    navController.navigate(Screens.CreateClassroom.route)
                }
            )
        }
        composable(route = Screens.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            SignUpScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent,
                onNavigateToSignIn = {
                    navController.navigate(Screens.SignIn.route)
                },
                onNavigateToClassroom = {
                    navController.navigate(BottomBarScreens.Classroom.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToCreateClassroom = {
                    navController.navigate(Screens.CreateClassroom.route)
                }
            )
        }
        composable(route = Screens.Practice.route) {
            PracticeScreen()
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen()
        }
        composable(route = Screens.CreateClassroom.route) {
            CreateClassroomScreen()
        }
    }
}