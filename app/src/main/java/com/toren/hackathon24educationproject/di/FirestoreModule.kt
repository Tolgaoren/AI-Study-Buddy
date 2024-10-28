package com.toren.hackathon24educationproject.di

import com.google.firebase.firestore.FirebaseFirestore
import com.toren.hackathon24educationproject.data.repository.FirestoreRepositoryImpl
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.model.Teacher
import com.toren.hackathon24educationproject.domain.repository.FirestoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirestoreRepository(
        firestore: FirebaseFirestore,
        student: Student,
        classroom: Classroom,
        teacher: Teacher
    ): FirestoreRepository = FirestoreRepositoryImpl(
        firestore, student, classroom, teacher
    )

}