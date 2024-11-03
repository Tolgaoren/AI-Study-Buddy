package com.toren.hackathon24educationproject.presentation.sign_up

object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val fullName: String = "",
        val classroomCode: String = "",
        val lastItemVisibility: Boolean = false
    )
    sealed class UiEvent {
        data class OnEmailChange(val email: String) : UiEvent()
        data class OnPasswordChange(val password: String) : UiEvent()
        data class OnFullNameChange(val name: String) : UiEvent()
        data class OnClassroomCodeChange(val code: String) : UiEvent()
        object OnSignInClick : UiEvent()
        object OnSignUpClick : UiEvent()
        object OnCreateClassroomClick : UiEvent()
        object OnLastItemVisibilityChange : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToClassroom : UiEffect()
        object NavigateToSignIn : UiEffect()
        object NavigateToCreateClassroom : UiEffect()
    }
}