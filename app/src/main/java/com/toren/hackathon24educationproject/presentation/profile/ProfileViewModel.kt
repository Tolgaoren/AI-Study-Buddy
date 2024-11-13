package com.toren.hackathon24educationproject.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.model.Teacher
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
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
class ProfileViewModel @Inject constructor(
    private val student: Student,
    private val authRepository: AuthRepository,
    private val classroom: Classroom,
    private val teacher: Teacher
): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileContract.UiState())
    val uiState: StateFlow<ProfileContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<ProfileContract.UiEffect>() }
    val uiEffect: Flow<ProfileContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        getStudent()
    }

    fun onEvent(event: ProfileContract.UiEvent) {
        when (event) {
            is ProfileContract.UiEvent.Refresh -> refresh()
            is ProfileContract.UiEvent.SignOutClick -> signOut()
            else -> {}
        }
    }

    private fun getStudent() = viewModelScope.launch {
        updateUiState {
            copy(
                fullName = student.fullName,
                avatar = student.avatar,
                level = student.level / 100,
                progress = student.level % 100 / 100f,
                badges = student.badges
            )
        }

    }

    private fun refresh() {
        getStudent()
    }

    private fun signOut() {
        viewModelScope.launch {
            when (val result =authRepository.signOut()) {
                is Resource.Error -> emitUiEffect(ProfileContract.UiEffect.ShowToast(result.message ?: ""))
                is Resource.Loading -> updateUiState { copy(isLoading = true) }
                is Resource.Success -> {
                    updateUiState { copy(isLoading = false) }
                    updateObjects()
                    emitUiEffect(ProfileContract.UiEffect.NavigateToLoginScreen)
                }
            }
        }
    }

    private fun updateObjects() {
        student.reset()
        classroom.reset()
        teacher.reset()
    }

    private fun updateUiState(block: ProfileContract.UiState.() -> ProfileContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: ProfileContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}