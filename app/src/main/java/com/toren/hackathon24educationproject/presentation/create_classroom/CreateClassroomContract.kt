package com.toren.hackathon24educationproject.presentation.create_classroom

object CreateClassroomContract {
    data class UiState(
        val isLoading: Boolean = false,
        val classroomName: String = "",
        val teacherName: String = "",
        val email: String = "",
        val password: String = "",
    )
    sealed class UiEvent {
        data class OnClassroomNameChange(val name: String) : UiEvent()
        data class OnTeacherNameChange(val name: String) : UiEvent()
        data class OnEmailChange(val email: String) : UiEvent()
        data class OnPasswordChange(val password: String) : UiEvent()
        object OnSignInClick : UiEvent()
        object OnSignUpClick : UiEvent()
        object OnCreateClassroomClick : UiEvent()
    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToClassroom : UiEffect()
        object NavigateToSignIn : UiEffect()
        object NavigateToSignUp : UiEffect()
    }
}