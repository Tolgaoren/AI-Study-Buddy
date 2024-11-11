package com.toren.hackathon24educationproject.presentation.create_classroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Teacher
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
class CreateClassroomViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private var classroom: Classroom,
    private var teacher: Teacher
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateClassroomContract.UiState())
    val uiState: StateFlow<CreateClassroomContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<CreateClassroomContract.UiEffect>() }
    val uiEffect: Flow<CreateClassroomContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onEvent(event: CreateClassroomContract.UiEvent) {
        when (event) {
            is CreateClassroomContract.UiEvent.OnClassroomNameChange -> updateUiState { copy(classroomName = event.name) }
            is CreateClassroomContract.UiEvent.OnTeacherNameChange -> updateUiState { copy(teacherName = event.name) }
            is CreateClassroomContract.UiEvent.OnEmailChange -> updateUiState { copy(email = event.email) }
            is CreateClassroomContract.UiEvent.OnPasswordChange -> updateUiState { copy(password = event.password) }
            is CreateClassroomContract.UiEvent.OnGradeChange -> updateUiState { copy(grade = event.grade) }
            is CreateClassroomContract.UiEvent.OnCreateClassroomClick -> createClassroom()
            is CreateClassroomContract.UiEvent.OnSignInClick -> signIn()
            is CreateClassroomContract.UiEvent.OnSignUpClick -> signUp()
            is CreateClassroomContract.UiEvent.OnLastItemVisibilityChange -> updateUiState { copy(lastItemVisibility = !lastItemVisibility) }
        }
    }

    private fun createClassroom() = viewModelScope.launch {
        val cid = "c" + System.currentTimeMillis().toString()
        updateUiState {
            copy(
                isLoading = true,
                classroomId = cid
            )
        }
        classroom.id = cid
        classroom.name = uiState.value.classroomName
        classroom.grade = uiState.value.grade
        when (val result = firestoreRepository.createClassroom()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                tryToSignIn()
            }
            is Resource.Error -> {
                emitUiEffect(CreateClassroomContract.UiEffect.ShowToast(result.message.toString()))
                updateUiState { copy(isLoading = false) }
            }
        }

    }

    private fun tryToSignIn() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signIn(uiState.value.email, uiState.value.password)) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Error -> {
                tryToSignUp()
            }
            is Resource.Success -> saveTeacher()
        }
    }

    private fun tryToSignUp() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signUp(uiState.value.email, uiState.value.password)) {
            is Resource.Error -> {
                emitUiEffect(CreateClassroomContract.UiEffect.ShowToast(result.message.toString()))
                updateUiState { copy(isLoading = false) }
            }
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> saveTeacher()
        }
    }

    private fun saveTeacher() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        teacher.id = authRepository.getUserUid()
        teacher.fullName = uiState.value.teacherName
        teacher.classroomIds = listOf(classroom.id)

        classroom.name = uiState.value.classroomName
        classroom.grade = uiState.value.grade
        classroom.id = uiState.value.classroomId
        classroom.teacherIds = listOf(teacher.id)

        when (val result = firestoreRepository.saveTeacher()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                getTeacher()
            }
            is Resource.Error -> {
                emitUiEffect(CreateClassroomContract.UiEffect.ShowToast(result.message.toString()))
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun getTeacher() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = firestoreRepository.getTeacher()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                teacher = result.data!!
                println(teacher)
                getClassroom()
            }
            is Resource.Error -> {
                emitUiEffect(CreateClassroomContract.UiEffect.ShowToast(result.message.toString()))
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun getClassroom() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = firestoreRepository.getClassroom()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {
                classroom = result.data!!
                println(classroom)
                emitUiEffect(CreateClassroomContract.UiEffect.NavigateToTeacher)
            }
            is Resource.Error -> {
                emitUiEffect(CreateClassroomContract.UiEffect.ShowToast(result.message.toString()))
                updateUiState { copy(isLoading = false) }
            }

        }
    }

    private fun signIn() = viewModelScope.launch {
        emitUiEffect(CreateClassroomContract.UiEffect.NavigateToSignIn)
    }

    private fun signUp() = viewModelScope.launch {
        emitUiEffect(CreateClassroomContract.UiEffect.NavigateToSignUp)
    }

    private fun updateUiState(block: CreateClassroomContract.UiState.() -> CreateClassroomContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: CreateClassroomContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}