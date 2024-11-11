package com.toren.hackathon24educationproject.presentation.sign_in

object SignInContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val lastItemVisibility: Boolean = false
    )
    sealed class UiEvent {
        data class OnEmailChange(val email: String) : UiEvent()
        data class OnPasswordChange(val password: String) : UiEvent()
        data object OnSignInClick : UiEvent()
        data object OnSignUpClick : UiEvent()
        data object OnCreateClassroomClick : UiEvent()
        data object OnLastItemVisibilityChange : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToClassroom : UiEffect()
        data object NavigateToSignUp : UiEffect()
        data object NavigateToCreateClassroom : UiEffect()
        data object NavigateToTeacher : UiEffect()
    }

}