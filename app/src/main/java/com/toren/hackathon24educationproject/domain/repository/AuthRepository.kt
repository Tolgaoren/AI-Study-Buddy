package com.toren.hackathon24educationproject.domain.repository

import com.toren.hackathon24educationproject.domain.model.Resource

interface AuthRepository {

    suspend fun isUserAuthenticated(): Boolean

    suspend fun getUserUid() : String

    suspend fun signIn(email: String, password: String): Resource<Boolean>

    suspend fun signOut(): Resource<Boolean>

    suspend fun signUp(email: String, password: String): Resource<Boolean>
}