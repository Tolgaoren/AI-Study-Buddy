package com.toren.hackathon24educationproject.presentation.subject_explanation

object SubjectExplanationContract {
    data class UiState(
        val isLoading: Boolean = false,
        val subject: String = "",
        val explanation: String = "",
        val error: String? = null,
        val text: String = "",
        val isAnswerFocused: Boolean = false,
    )

    sealed class UiEvent {
        data object OnReplyClick : UiEvent()
        data class OnExplanationChange(val text: String) : UiEvent()
        data class OnTextChange(val text: String) : UiEvent()
        data object OnAnswerFocused : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
    }
}