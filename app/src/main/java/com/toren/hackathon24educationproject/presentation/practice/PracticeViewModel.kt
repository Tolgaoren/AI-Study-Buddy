package com.toren.hackathon24educationproject.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
import com.toren.hackathon24educationproject.domain.repository.GeminiRepository
import com.toren.hackathon24educationproject.presentation.practice.PracticeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val geminiRepository: GeminiRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // history
    private val history = mutableListOf<String>()

    init {
        answerQuestion("matematik", 4)
    }

    fun onAction(action: PracticeContract.UiAction) {
        when (action) {
            is PracticeContract.UiAction.AnswerQuestion -> answerQuestion(action.answer)

            is PracticeContract.UiAction.ExplainQuestion -> {}

            is PracticeContract.UiAction.NextQuestion -> {}

            is PracticeContract.UiAction.Quit -> {}
        }
    }

    private fun answerQuestion(subject: String, grade: Int = 4) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val result = geminiRepository.startChat(
            prompt = "bana $subject ile ilgili $grade sınıfı seviyesinde bir soru sor",
            history = history.map {
                Content(
                    role = "model",
                    parts = listOf(TextPart(it))
                )
            }
        )
        when (result) {
            is Resource.Error -> println(result.message)
            is Resource.Loading -> println(result.data)
            is Resource.Success -> println(result.data)
        }
    }



}