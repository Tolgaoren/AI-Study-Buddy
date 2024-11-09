package com.toren.hackathon24educationproject.presentation.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private var student: Student,
    private var classroom: Classroom
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpContract.UiState())
    val uiState: StateFlow<SignUpContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignUpContract.UiEffect>() }
    val uiEffect: Flow<SignUpContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onEvent(event: SignUpContract.UiEvent) {
        when (event) {
            is SignUpContract.UiEvent.OnEmailChange -> updateUiState { copy(email = event.email) }
            is SignUpContract.UiEvent.OnPasswordChange -> updateUiState { copy(password = event.password) }
            is SignUpContract.UiEvent.OnClassroomCodeChange -> updateUiState { copy(classroomCode = event.code) }
            is SignUpContract.UiEvent.OnFullNameChange -> updateUiState { copy(fullName = event.name) }
            is SignUpContract.UiEvent.OnSignInClick -> signIn()
            is SignUpContract.UiEvent.OnSignUpClick -> signUp()
            is SignUpContract.UiEvent.OnCreateClassroomClick -> createClassroom()
            is SignUpContract.UiEvent.OnLastItemVisibilityChange -> updateUiState { copy(lastItemVisibility = !lastItemVisibility) }
            is SignUpContract.UiEvent.OnAvatarChange -> {
                updateUiState { copy(avatarId = event.avatarId) }
                Log.d("Avatar", uiState.value.avatarId.toString())
            }
        }
    }

    private fun signIn() = viewModelScope.launch {
        emitUiEffect(SignUpContract.UiEffect.NavigateToSignIn)
    }

    private fun createClassroom() = viewModelScope.launch {
        emitUiEffect(SignUpContract.UiEffect.NavigateToCreateClassroom)
    }

    private fun signUp() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signUp(uiState.value.email, uiState.value.password)) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {
                saveStudent()
            }

            is Resource.Error -> {
                emitUiEffect(SignUpContract.UiEffect.ShowToast(result.message.toString()))
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun saveStudent() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        student.id = authRepository.getUserUid()
        student.fullName = uiState.value.fullName
        student.classroomId = uiState.value.classroomCode
        student.avatar = uiState.value.avatarId

        classroom.id = uiState.value.classroomCode

        when (val result = firestoreRepository.saveStudent()) {
            is Resource.Loading -> {
                updateUiState { copy(isLoading = true) }
            }
            is Resource.Success -> {
                Log.d("Student", result.data.toString() )
                getClassroom()
            }
            is Resource.Error -> {
                Log.d("Student", result.message.toString())
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun getClassroom() = viewModelScope.launch {

        when (val result = firestoreRepository.getClassroom()) {
            is Resource.Loading -> {
                updateUiState { copy(isLoading = true) }
            }

            is Resource.Success -> {
                classroom.id = result.data?.id ?: ""
                classroom.name = result.data?.name ?: ""
                classroom.grade = result.data?.grade ?: 1
                classroom.subjects = result.data?.subjects ?: listOf()
                emitUiEffect(SignUpContract.UiEffect.NavigateToClassroom)
            }
            is Resource.Error -> {
                Log.d("Classroom", result.message.toString())
                updateUiState { copy(isLoading = false) }
            }
        }
    }


    private fun updateUiState(block: SignUpContract.UiState.() -> SignUpContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: SignUpContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}