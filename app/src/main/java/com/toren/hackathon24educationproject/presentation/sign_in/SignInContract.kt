package com.toren.hackathon24educationproject.presentation.sign_in

import com.toren.hackathon24educationproject.domain.model.Role

object SignInContract {
    data class UiState(
        val isLoading: Boolean = false,
        val email: String = "",
        val password: String = "",
        val role: Role
    )
    sealed class UiEvent {
        data class OnEmailChange(val email: String) : UiEvent()
        data class OnPasswordChange(val password: String) : UiEvent()
        data class OnRoleChange(val role: Role) : UiEvent()
        object OnSignInClick : UiEvent()
    }

    sealed class UiEffect {
        data class ShowError(val message: String) : UiEffect()
        object NavigateToClassroom : UiEffect()
    }

}