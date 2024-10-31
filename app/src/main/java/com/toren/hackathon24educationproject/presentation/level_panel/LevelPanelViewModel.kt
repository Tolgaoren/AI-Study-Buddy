package com.toren.hackathon24educationproject.presentation.level_panel

import androidx.lifecycle.ViewModel
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.presentation.level_panel.LevelPanelContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class LevelPanelViewModel @Inject constructor(
    private val student: Student
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<LevelPanelContract.UiEffect>() }
    val uiEffect: Flow<LevelPanelContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init { // Student nesnesini kontrol ediyoruz
        println("Student injected in ViewModel: $student")
        updateUiState {
            copy(
                fullName = student.fullName,
                level = student.level / 100,
                progress = (student.level % 100) / 100f * 100
            )
        }// Test amaçlı bir log ekleyelim
        println("student.fullName: ${student.fullName}, level: ${student.level}")
    }

    fun onEvent(event: LevelPanelContract.UiEvent) {
        when (event) {
            is LevelPanelContract.UiEvent.OnLevelChange -> {}
            is LevelPanelContract.UiEvent.OnProgressChange -> {}
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: LevelPanelContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}