package com.toren.hackathon24educationproject.presentation.teacher

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.repository.FirestoreRepository
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
class TeacherViewModel
@Inject constructor(
    private val firestoreRepository: FirestoreRepository,
    private val classroom: Classroom,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherContract.UiState())
    val uiState: StateFlow<TeacherContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<TeacherContract.UiEffect>() }
    val uiEffect: Flow<TeacherContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        val subjects = classroom.subjects
        updateUiState { copy(subjects = subjects) }
        getStudents()
    }

    fun onEvent(event: TeacherContract.UiEvent) {
        when (event) {
            is TeacherContract.UiEvent.AddSubject -> {
                updateUiState { copy(subjects = subjects + event.subject) }
            }
        }
    }

    private fun getStudents() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = firestoreRepository.getStudents()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {
                Log.d("TeacherViewModel", "getStudents: ${result.data}")
                updateUiState {
                    copy(
                        students = result.data?.sortedByDescending { it.level }?.map {
                            it.copy(
                                level = it.level / 100
                            )

                        } ?: emptyList(),
                        isLoading = false,
                    )
                }
            }
            is Resource.Error -> {
                Log.d("TAG", "getStudents: ${result.message}")
                updateUiState {
                    copy(
                        error = result.message,
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun onAddSubjectClick() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }

    }

    private fun updateUiState(block: TeacherContract.UiState.() -> TeacherContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: TeacherContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }


}