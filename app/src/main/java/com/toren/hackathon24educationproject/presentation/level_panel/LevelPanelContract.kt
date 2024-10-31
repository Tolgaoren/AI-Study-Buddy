package com.toren.hackathon24educationproject.presentation.level_panel

object LevelPanelContract {
    data class UiState(
        val isLoading: Boolean = false,
        val level: Int = 0,
        val progress: Float = 0f,
        val fullName: String = "",
    )
    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
    }

    sealed class UiEvent {
        data class OnLevelChange(val level: Int) : UiEvent()
        data class OnProgressChange(val progress: Float) : UiEvent()
    }
}