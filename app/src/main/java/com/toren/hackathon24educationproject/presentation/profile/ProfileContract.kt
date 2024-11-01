package com.toren.hackathon24educationproject.presentation.profile


object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val fullName: String = "",
        val avatar: Int = 0,
        val level: Int = 0,
        val progress: Float = 0f,
    )
    sealed class UiEvent {
        object Refresh : UiEvent()
    }
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
    }

}