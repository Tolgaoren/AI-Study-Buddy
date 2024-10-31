package com.toren.hackathon24educationproject.presentation.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private val student: Student
): ViewModel() {

    private val _uiState = MutableStateFlow(SignInContract.UiState())
    val uiState: StateFlow<SignInContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignInContract.UiEffect>() }
    val uiEffect: Flow<SignInContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        updateUiState { copy(isLoading = true) }
        isUserLoggedIn()
    }

    fun onEvent(event: SignInContract.UiEvent) {
        when (event) {
            is SignInContract.UiEvent.OnEmailChange -> updateUiState { copy(email = event.email) }
            is SignInContract.UiEvent.OnPasswordChange -> updateUiState { copy(password = event.password) }
            is SignInContract.UiEvent.OnSignInClick -> signIn()
            is SignInContract.UiEvent.OnSignUpClick -> signUp()
            is SignInContract.UiEvent.OnCreateClassroomClick -> createClassroom()
        }
    }

    private fun isUserLoggedIn() = viewModelScope.launch {
        if (authRepository.isUserAuthenticated()) {
            getUserInfo()
        } else {
            updateUiState { copy(isLoading = false) }
        }
    }
    private fun getUserInfo() = viewModelScope.launch {
        when(val result = firestoreRepository.getStudent()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {
                student.id = result.data?.id ?: authRepository.getUserUid()
                student.fullName = result.data?.fullName ?: ""
                student.classroomId = result.data?.classroomId ?: ""
                student.level = result.data?.level ?: 0
                println("sign in: "+student)
                emitUiEffect(SignInContract.UiEffect.NavigateToClassroom)
        }
            is Resource.Error -> {
                Log.d("TAG", "getUserInfo: ${result.message}")
                updateUiState { copy(isLoading = false) }
            }
        }

    }

    private fun signIn() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signIn(uiState.value.email, uiState.value.password)) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {

                emitUiEffect(SignInContract.UiEffect.NavigateToClassroom)
            }

            is Resource.Error -> {
                emitUiEffect(SignInContract.UiEffect.ShowToast("Kullanıcı adı veya şifre hatalı"))
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun signUp() = viewModelScope.launch {
        emitUiEffect(SignInContract.UiEffect.NavigateToSignUp)
    }

    private fun createClassroom() = viewModelScope.launch {
        emitUiEffect(SignInContract.UiEffect.NavigateToCreateClassroom)
    }

    private fun updateUiState(block: SignInContract.UiState.() -> SignInContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: SignInContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}