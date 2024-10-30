package com.toren.hackathon24educationproject.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun onEvent(event: PracticeContract.UiEvent) {
        when (event) {
            is PracticeContract.UiEvent.OnAnswerChange -> updateUiState { copy(answer = event.answer) }
            is PracticeContract.UiEvent.OnQuestionChange -> updateUiState { copy(question = event.question) }
            is PracticeContract.UiEvent.OnAnswerFocused -> updateUiState { copy(isAnswerFocused = !isAnswerFocused) }
            is PracticeContract.UiEvent.OnExplanationChange -> updateUiState { copy(explanation = event.explanation) }
            is PracticeContract.UiEvent.OnAnswerClick -> answerQuestion()
            is PracticeContract.UiEvent.OnExplainClick -> explainAnswer()
            is PracticeContract.UiEvent.OnNextClick -> nextQuestion()
            is PracticeContract.UiEvent.OnQuitClick -> onQuitClick()
        }
    }

    fun setSubject(subject: String) {
        updateUiState { copy(subject = subject) }
        askQuestion(subject)
    }

    private fun askQuestion(subject: String) = viewModelScope.launch {
        when (val result = geminiRepository.startChat(subject)) {
            is Resource.Error -> {
                emitUiEffect(PracticeContract.UiEffect.ShowToast(result.message ?: "Error"))
            }
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                updateUiState { copy(question = result.data ?: "") }
            }
        }
    }

    private fun answerQuestion() = viewModelScope.launch {

    }

    private fun explainAnswer() = viewModelScope.launch {

    }

    private fun nextQuestion() = viewModelScope.launch {

    }

    private fun onQuitClick() = viewModelScope.launch {
        emitUiEffect(PracticeContract.UiEffect.GoToBackScreen)
    }

/*    private fun answerQuestion(subject: String, grade: Int = 4) = viewModelScope.launch {
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
    }*/


    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: PracticeContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}