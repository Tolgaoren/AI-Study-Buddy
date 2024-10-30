package com.toren.hackathon24educationproject.presentation.choose_subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
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
class ChooseSubjectViewModel
@Inject constructor(
    private val classroom: Classroom
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChooseSubjectContract.UiState())
    val uiState: StateFlow<ChooseSubjectContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ChooseSubjectContract.UiEffect>() }
    val uiEffect: Flow<ChooseSubjectContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        updateUiState { copy( subjects = classroom.subjects ) }
    }

    fun onEvent(event: ChooseSubjectContract.UiEvent) {
        when (event) {
            is ChooseSubjectContract.UiEvent.OnSubjectClick -> onSubjectClick()
        }
    }

    private fun onSubjectClick() = viewModelScope.launch {
        emitUiEffect(ChooseSubjectContract.UiEffect.NavigateToPractice)
    }

    private fun updateUiState(block: ChooseSubjectContract.UiState.() -> ChooseSubjectContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: ChooseSubjectContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}