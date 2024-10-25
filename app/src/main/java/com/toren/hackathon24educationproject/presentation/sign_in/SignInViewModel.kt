package com.toren.hackathon24educationproject.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
import com.toren.hackathon24educationproject.domain.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestoreRepository: FirestoreRepository,
    private val student: Student
): ViewModel() {

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() = viewModelScope.launch {
        if (authRepository.isUserAuthenticated()) {

        }
    }

    private fun saveStudent() {
        firestoreRepository.saveStudent()
    }

}