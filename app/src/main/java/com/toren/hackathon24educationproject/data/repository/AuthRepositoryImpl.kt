package com.toren.hackathon24educationproject.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
): AuthRepository {

    override suspend fun isUserAuthenticated(): Boolean = auth.currentUser != null

    override suspend fun getUserUid() = auth.uid.toString()

    override suspend fun signIn(email: String, password: String): Resource<Boolean> {
        return try {
            Resource.Loading<Boolean>()
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user != null)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun signOut(): Resource<Boolean> {
        return try {
            Resource.Loading<Boolean>()
            auth.signOut()
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun signUp(email: String, password: String): Resource<Boolean> {
        return try {
            Resource.Loading<Boolean>()
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(result.user != null)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

}