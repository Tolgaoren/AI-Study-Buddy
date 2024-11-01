package com.toren.hackathon24educationproject.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.toren.hackathon24educationproject.presentation.choose_subject.ChooseSubjectScreen
import com.toren.hackathon24educationproject.presentation.choose_subject.ChooseSubjectViewModel
import com.toren.hackathon24educationproject.presentation.classroom.ClassroomScreen
import com.toren.hackathon24educationproject.presentation.classroom.ClassroomViewModel
import com.toren.hackathon24educationproject.presentation.create_classroom.CreateClassroomScreen
import com.toren.hackathon24educationproject.presentation.create_classroom.CreateClassroomViewModel
import com.toren.hackathon24educationproject.presentation.practice.PracticeScreen
import com.toren.hackathon24educationproject.presentation.practice.PracticeViewModel
import com.toren.hackathon24educationproject.presentation.profile.ProfileScreen
import com.toren.hackathon24educationproject.presentation.profile.ProfileViewModel
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
            val viewModel: ClassroomViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ClassroomScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent
            )
        }
        composable(
            route = Screens.Practice.route + "/{subject}",
            arguments = listOf(navArgument("subject") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val viewModel: PracticeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            val subject = navBackStackEntry.arguments?.getString("subject") ?: ""
            LaunchedEffect (subject){
                viewModel.setSubject(subject)
            }
            PracticeScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent
            )
        }
        composable(route = BottomBarScreens.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProfileScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent
            )
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
        composable(route = BottomBarScreens.ChooseSubject.route) {
            val viewModel: ChooseSubjectViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ChooseSubjectScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent,
                onNavigateToPractice = { subject ->
                    navController.navigate(Screens.Practice.route + "/$subject")
                }
            )
        }
        composable(route = Screens.CreateClassroom.route) {
            val viewModel: CreateClassroomViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            CreateClassroomScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                uiEvent = viewModel::onEvent,
                onNavigateToClassroom = {
                    navController.navigate(BottomBarScreens.Classroom.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToSignIn = {
                    navController.navigate(Screens.SignIn.route)
                },
                onNavigateToSignUp = {
                    navController.navigate(Screens.SignUp.route)
                }
            )
        }
    }
}