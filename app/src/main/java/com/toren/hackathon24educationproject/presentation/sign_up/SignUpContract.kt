package com.toren.hackathon24educationproject.presentation.sign_up

object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val fullName: String = "",
        val classroomCode: String = "",
        val lastItemVisibility: Boolean = false,
        val avatarId: Int = 1,
    )
    sealed class UiEvent {
        data class OnEmailChange(val email: String) : UiEvent()
        data class OnPasswordChange(val password: String) : UiEvent()
        data class OnFullNameChange(val name: String) : UiEvent()
        data class OnClassroomCodeChange(val code: String) : UiEvent()
        data class OnAvatarChange(val avatarId: Int) : UiEvent()
        data object OnSignInClick : UiEvent()
        data object OnSignUpClick : UiEvent()
        data object OnCreateClassroomClick : UiEvent()
        data object OnLastItemVisibilityChange : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToClassroom : UiEffect()
        data object NavigateToSignIn : UiEffect()
        data object NavigateToCreateClassroom : UiEffect()
    }
}