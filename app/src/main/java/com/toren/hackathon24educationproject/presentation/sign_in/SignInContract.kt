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
        object OnSignInClick : UiEvent()
        object OnSignUpClick : UiEvent()
        object OnCreateClassroomClick : UiEvent()
        object OnLastItemVisibilityChange : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToClassroom : UiEffect()
        object NavigateToSignUp : UiEffect()
        object NavigateToCreateClassroom : UiEffect()
    }

}