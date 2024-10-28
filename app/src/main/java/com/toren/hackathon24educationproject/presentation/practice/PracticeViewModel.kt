package com.toren.hackathon24educationproject.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
import com.toren.hackathon24educationproject.domain.repository.GeminiRepository
import com.toren.hackathon24educationproject.presentation.practice.PracticeContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val geminiRepository: GeminiRepository,
    private var student: Student
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<PracticeContract.UiEffect>() }
    val uiEffect: Flow<PracticeContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    // history
    private val history = mutableListOf<String>()

    init {
        answerQuestion("matematik", 4)
    }

    fun onEvent(event: PracticeContract.UiEvent) {
        when (event) {
            is PracticeContract.UiEvent.OnAnswerChange -> updateUiState { copy(answer = event.answer) }
            is PracticeContract.UiEvent.OnAnswerClick -> TODO()
            is PracticeContract.UiEvent.OnExplainClick -> TODO()
            is PracticeContract.UiEvent.OnExplanationChange -> TODO()
            is PracticeContract.UiEvent.OnNextClick -> TODO()
            is PracticeContract.UiEvent.OnQuestionChange -> updateUiState { copy(question = event.question) }
            is PracticeContract.UiEvent.OnQuitClick -> TODO()
            is PracticeContract.UiEvent.OnAnswerFocused -> updateUiState { copy(isAnswerFocused = !isAnswerFocused) }
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
            is Resource.Success -> {
                updateUiState { copy(question = result.data.toString()) }
            }
        }
    }


    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: PracticeContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}