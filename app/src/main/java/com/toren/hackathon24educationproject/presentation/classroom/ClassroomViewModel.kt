package com.toren.hackathon24educationproject.presentation.classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
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
class ClassroomViewModel @Inject constructor(
    private val classroom: Classroom,
    private val student: Student,
    private val firebaseRepository: FirestoreRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClassroomContract.UiState())
    val uiState: StateFlow<ClassroomContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ClassroomContract.UiEffect>() }
    val uiEffect: Flow<ClassroomContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        getStudents()
    }

    fun onEvent(event: ClassroomContract.UiEvent) {
        when (event) {

            else -> {}
        }
    }

    private fun getStudents() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = firebaseRepository.getStudents()) {
            is Resource.Error -> updateUiState {
                copy(
                    error = result.message,
                    isLoading = false
                )
            }

            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> updateUiState {
                copy(
                    students = result.data?.map {
                        it.copy( level = it.level / 100)

                    } ?: emptyList(),
                    isLoading = false
                )
            }
        }
    }

    private fun updateUiState(block: ClassroomContract.UiState.() -> ClassroomContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: ClassroomContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}