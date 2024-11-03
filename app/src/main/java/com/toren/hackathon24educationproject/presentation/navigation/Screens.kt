package com.toren.hackathon24educationproject.presentation.navigation

sealed class Screens(
    val route: String,
    val title: String,
) {
    object CreateClassroom : Screens(
        route = "create_classroom",
        title = "Create Classroom",
    )

    object Profile : Screens(
        route = "profile",
        title = "Profile",
    )

    object Practice : Screens(
        route = "practice/{subject}",
        title = "practice"
    )

    object SignIn : Screens(
        route = "sign_in",
        title = "Sign In",
    )

    object SignUp : Screens(
        route = "sign_up",
        title = "Sign Up",
    )
    object SubjectExplanation : Screens(
        route = "subject_explanation",
        title = "Subject Explanation",
    )

}