package com.toren.hackathon24educationproject.presentation.teacher

import androidx.lifecycle.ViewModel
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
import com.toren.hackathon24educationproject.domain.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TeacherViewModel
@Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherContract.UiState())
    val uiState: StateFlow<TeacherContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<TeacherContract.UiEffect>() }
    val uiEffect: Flow<TeacherContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onEvent(event: TeacherContract.UiEvent) {
        when (event) {
            is TeacherContract.UiEvent.AddSubject -> {}
        }
    }

    private fun updateUiState(block: TeacherContract.UiState.() -> TeacherContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: TeacherContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }


}