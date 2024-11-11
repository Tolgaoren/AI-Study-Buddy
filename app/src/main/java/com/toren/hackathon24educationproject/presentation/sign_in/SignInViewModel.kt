package com.toren.hackathon24educationproject.presentation.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Role
import com.toren.hackathon24educationproject.domain.model.Student
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
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private val student: Student,
    private var classroom: Classroom,
    private val teacher: Teacher
): ViewModel() {

    private val _uiState = MutableStateFlow(SignInContract.UiState())
    val uiState: StateFlow<SignInContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<SignInContract.UiEffect>() }
    val uiEffect: Flow<SignInContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    private var role: Role? = null

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
            is SignInContract.UiEvent.OnLastItemVisibilityChange -> updateUiState { copy(lastItemVisibility = !lastItemVisibility) }
        }
    }

    private fun isUserLoggedIn() = viewModelScope.launch {
        if (authRepository.isUserAuthenticated()) {
            getUserInfo()
        } else {
            updateUiState { copy(isLoading = false) }
        }
    }

    private fun signIn() = viewModelScope.launch {
        updateUiState { copy(isLoading = true) }
        when (val result = authRepository.signIn(uiState.value.email, uiState.value.password)) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {
                getUserInfo()
            }

            is Resource.Error -> {
                emitUiEffect(SignInContract.UiEffect.ShowToast("Kullanıcı adı veya şifre hatalı"))
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun getUserInfo() = viewModelScope.launch {
        when(val result = firestoreRepository.getStudent()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }

            is Resource.Success -> {
                role = Role.STUDENT
                updateStudent(result.data?: Student())
                getClassroom()
        }
            is Resource.Error -> {
                Log.d("TAG", "getUserInfo: ${result.message}")
                updateUiState { copy(isLoading = true) }
                getTeacher()
            }
        }

    }

    private fun getTeacher() = viewModelScope.launch {
        when(val result = firestoreRepository.getTeacher()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                role = Role.TEACHER
                updateTeacher(result.data?: Teacher())
                updateClassroomId()
                getClassroom()
            }
            is Resource.Error -> {
                Log.d("TAG", "getTeacher: ${result.message}")
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun getClassroom() = viewModelScope.launch {
        when (val result = firestoreRepository.getClassroom()) {
            is Resource.Loading -> updateUiState { copy(isLoading = true) }
            is Resource.Success -> {
                updateClassroom(result.data?: Classroom())
                navigate()
            }

            is Resource.Error -> {
                Log.d("TAG", "getClassroom: ${result.message}")
                updateUiState { copy(isLoading = false) }
            }
        }
    }

    private fun navigate() = viewModelScope.launch {
        if (role == Role.STUDENT) {
            emitUiEffect(SignInContract.UiEffect.NavigateToClassroom)
        } else if (role == Role.TEACHER) {
            emitUiEffect(SignInContract.UiEffect.NavigateToTeacher)
        }
    }

    private fun signUp() = viewModelScope.launch {
        emitUiEffect(SignInContract.UiEffect.NavigateToSignUp)
    }

    private fun createClassroom() = viewModelScope.launch {
        emitUiEffect(SignInContract.UiEffect.NavigateToCreateClassroom)
    }

    private fun updateStudent(result: Student) {
        student.id = result.id
        student.fullName = result.fullName
        student.classroomId = result.classroomId
        student.level = result.level
        student.avatar = result.avatar
        student.badges = result.badges
        classroom.id = result.classroomId
    }

    private fun updateTeacher(result: Teacher) {
        teacher.id = result.id
        teacher.fullName = result.fullName
        teacher.classroomIds = result.classroomIds
        classroom.id = result.classroomIds[0]
    }

    private fun updateClassroomId() {
        classroom.id = teacher.classroomIds[0]
    }

    private fun updateClassroom(result: Classroom) {
        classroom.id = result.id
        classroom.name = result.name
        classroom.grade = result.grade
        classroom.subjects = result.subjects
        classroom.teacherIds = result.teacherIds
        classroom.studentIds = result.studentIds
    }

    private fun updateUiState(block: SignInContract.UiState.() -> SignInContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: SignInContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }
}