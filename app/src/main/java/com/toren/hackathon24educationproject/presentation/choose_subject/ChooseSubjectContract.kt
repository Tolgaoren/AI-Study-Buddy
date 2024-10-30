package com.toren.hackathon24educationproject.presentation.choose_subject

object ChooseSubjectContract {
    data class UiState(
        val isLoading: Boolean = false,
        val subjects: List<String> = emptyList(),
        val error: String? = null
    )

    sealed class UiEvent {
        data class OnSubjectClick(val subject: String) : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        object NavigateToPractice : UiEffect()
    }

}