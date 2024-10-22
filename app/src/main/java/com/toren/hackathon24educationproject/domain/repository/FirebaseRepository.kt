package com.toren.hackathon24educationproject.domain.repository

import com.toren.hackathon24educationproject.domain.model.Resource

interface FirebaseRepository {
    suspend fun isUserAuthenticated(): Boolean
    suspend fun signIn(email: String, password: String): Resource<Boolean>
    suspend fun signOut(): Resource<Boolean>
    suspend fun signUp(email: String, password: String): Resource<Boolean>
}