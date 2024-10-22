package com.toren.hackathon24educationproject.di

import com.google.firebase.auth.FirebaseAuth
import com.toren.hackathon24educationproject.data.repository.FirebaseRepositoryImpl
import com.toren.hackathon24educationproject.domain.repository.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseRepository(auth: FirebaseAuth): FirebaseRepository = FirebaseRepositoryImpl(auth)

}