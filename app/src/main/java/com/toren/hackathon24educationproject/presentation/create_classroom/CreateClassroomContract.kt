package com.toren.hackathon24educationproject.presentation.create_classroom

object CreateClassroomContract {
    data class UiState(
        val isLoading: Boolean = false,
        val classroomName: String = "",
        val teacherName: String = "",
        val email: String = "",
        val password: String = "",
        val grade: Int = 1,
        val lastItemVisibility: Boolean = false
    )
    sealed class UiEvent {
        data class OnClassroomNameChange(val name: String) : UiEvent()
        data class OnTeacherNameChange(val name: String) : UiEvent()
        data class OnEmailChange(val email: String) : UiEvent()
        data class OnPasswordChange(val password: String) : UiEvent()
        data class OnGradeChange(val grade: Int) : UiEvent()
        data object OnSignInClick : UiEvent()
        data object OnSignUpClick : UiEvent()
        data object OnCreateClassroomClick : UiEvent()
        data object OnLastItemVisibilityChange : UiEvent()

    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToClassroom : UiEffect()
        data object NavigateToSignIn : UiEffect()
        data object NavigateToSignUp : UiEffect()
    }
}