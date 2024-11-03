package com.toren.hackathon24educationproject.presentation.subject_explanation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.History
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.repository.GeminiRepository
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
class SubjectExplanationViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private var student: Student,
    private var history: History
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubjectExplanationContract.UiState())
    val uiState: StateFlow<SubjectExplanationContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SubjectExplanationContract.UiEffect>() }
    val uiEffect: Flow<SubjectExplanationContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onEvent(event: SubjectExplanationContract.UiEvent) {
        when (event) {
            is SubjectExplanationContract.UiEvent.OnExplanationChange -> updateUiState { copy(explanation = event.text) }
            is SubjectExplanationContract.UiEvent.OnReplyClick -> reply()
            is SubjectExplanationContract.UiEvent.OnTextChange -> updateUiState { copy(text = event.text) }
            is SubjectExplanationContract.UiEvent.OnAnswerFocused -> updateUiState { copy(isAnswerFocused = !isAnswerFocused) }
        }

    }

    fun setSubject(subject: String) {
        updateUiState {
            copy(
                subject = subject,
                isLoading = true
            )
        }
        getExplanation()
    }

    private fun reply() = viewModelScope.launch {
        when (val result = geminiRepository.reply(uiState.value.text)) {
            is Resource.Error ->{
                updateUiState { copy(isLoading = false, text = "") }
                emitUiEffect(SubjectExplanationContract.UiEffect.ShowToast(result.message ?: "Error"))

            }
               is Resource.Loading ->
                updateUiState { copy(isLoading = true,
                    text = ""
                ) }
            is Resource.Success ->
                updateUiState {
                    copy(
                        isLoading = false,
                        explanation = result.data ?: "",
                        text = ""
                    )
                }
        }
    }

    private fun getExplanation() = viewModelScope.launch {
        when (val result = geminiRepository.getExplanation(uiState.value.subject)) {
            is Resource.Error -> emitUiEffect(SubjectExplanationContract.UiEffect.ShowToast(result.message ?: "Error"))
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                updateUiState {
                    copy(
                        isLoading = false,
                        explanation = result.data ?: ""
                    )
                }
            }
        }
    }

    private fun updateUiState(block: SubjectExplanationContract.UiState.() -> SubjectExplanationContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: SubjectExplanationContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}