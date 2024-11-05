package com.toren.hackathon24educationproject.presentation.classroom

import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Student

object ClassroomContract {
    data class UiState(
        val isLoading: Boolean = false,
        val classroom: Classroom = Classroom(),
        val error: String? = null,
        val students: List<Student> = emptyList(),
        val url: String = "",
        val subjects: List<String> = emptyList(),
        )
    sealed class UiEvent {
        data class OnSubjectClick(val subject: String) : UiEvent()
    }

    sealed class UiEffect {
        data class ShowToast(val message: String) : UiEffect()
        data class NavigateToSubjectExplanation(val subject: String) : UiEffect()
    }

}