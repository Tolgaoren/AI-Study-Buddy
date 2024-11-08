package com.toren.hackathon24educationproject.presentation.teacher

import com.toren.hackathon24educationproject.domain.model.Student

object TeacherContract {
    data class UiState(
        val isLoading: Boolean = false,
        val students: List<Student> = emptyList(),
        val subjects: List<String> = emptyList(),
        val error: String? = null,
    )

    sealed class UiEvent {
        data class AddSubject(val subject: String) : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
    }
}