package com.toren.hackathon24educationproject.presentation.practice

object PracticeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val subject: String = "",
        val question: String = "",
        val answer: String = "",
        val isAnswerFocused: Boolean = false,
        val isAnswerCorrect: Boolean? = null,
        val error: String? = null,
        val level: Int = 0,
        val progress: Float = 0f,
        val fullName: String = "",
    )

    sealed class UiEvent {
        data class OnQuestionChange(val question: String) : UiEvent()
        data class OnAnswerChange(val answer: String) : UiEvent()
        data object OnAnswerClick : UiEvent()
        data object OnNextClick : UiEvent()
        data object OnQuitClick : UiEvent()
        data object OnAnswerFocused : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object GoToBackScreen : UiEffect()
    }
}