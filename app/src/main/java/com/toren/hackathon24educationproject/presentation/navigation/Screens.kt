package com.toren.hackathon24educationproject.presentation.navigation

sealed class Screens(
    val route: String,
    val title: String,
) {
    object CreateClassroom : Screens(
        route = "create_classroom",
        title = "Create Classroom",
    )

    object JoinClassroom : Screens(
        route = "join_classroom",
        title = "Join Classroom",
    )

    object Practice : Screens(
        route = "practice",
        title = "Practice",
    )

    object Profile : Screens(
        route = "profile",
        title = "Profile",
    )

    object SignIn : Screens(
        route = "sign_in",
        title = "Sign In",
    )

    object SignUp : Screens(
        route = "sign_up",
        title = "Sign Up",
    )

}