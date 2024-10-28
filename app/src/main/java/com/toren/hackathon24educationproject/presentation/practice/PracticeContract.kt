package com.toren.hackathon24educationproject.presentation.practice

object PracticeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val question: String = "",
        val answer: String = "",
        val explanation: String = "",
        val isAnswerFocused: Boolean = false,
        val isAnswerCorrect: Boolean = false,
        val error: String? = null
    )

    sealed class UiEvent {
        data class OnQuestionChange(val question: String) : UiEvent()
        data class OnAnswerChange(val answer: String) : UiEvent()
        data class OnExplanationChange(val explanation: String) : UiEvent()
        data class OnAnswerClick(val answer: String) : UiEvent()
        data object OnExplainClick : UiEvent()
        data object OnNextClick : UiEvent()
        data object OnQuitClick : UiEvent()
        data object OnAnswerFocused : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object GoToBackScreen : UiEffect()
    }
}