package com.toren.hackathon24educationproject.presentation.navigation

sealed class Screens(
    val route: String,
    val title: String,
) {
    data object CreateClassroom : Screens(
        route = "create_classroom",
        title = "Create Classroom",
    )
    data object Practice : Screens(
        route = "practice/{subject}",
        title = "practice"
    )
    data object SignIn : Screens(
        route = "sign_in",
        title = "Sign In",
    )
    data object SignUp : Screens(
        route = "sign_up",
        title = "Sign Up",
    )
    data object SubjectExplanation : Screens(
        route = "subject_explanation",
        title = "Subject Explanation",
    )
    data object Teacher : Screens(
        route = "teacher",
        title = "Teacher",
    )
}