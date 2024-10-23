package com.toren.hackathon24educationproject.presentation.practice

object PracticeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val question: String = "",
        val answers: List<String> = emptyList(),
        val error: String? = null
    )

    sealed class UiAction {
        data class AnswerQuestion(val answer: String) : UiAction()
        data object ExplainQuestion : UiAction()
        data object NextQuestion : UiAction()
        data object Quit : UiAction()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data object GoToBackScreen : UiEffect()
    }
}