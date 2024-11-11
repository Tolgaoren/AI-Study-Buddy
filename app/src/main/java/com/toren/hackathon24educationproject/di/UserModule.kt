package com.toren.hackathon24educationproject.di

import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.History
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.model.Teacher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {


    @Singleton
    @Provides
    fun provideStudent() : Student = Student(
        id = "",
        fullName = "",
        classroomId = "",
        level = 0,
        badges = listOf(),
        historyId = 0,
        avatar = 1
    )

    @Provides
    @Singleton
    fun provideClassroom() : Classroom = Classroom(
        id = "",
        name = "",
        studentIds = listOf(),
        teacherIds = listOf(),
        subjects = listOf(),
        grade = 1
    )

    @Provides
    @Singleton
    fun provideTeacher() : Teacher = Teacher(
        id = "",
        fullName = "",
        classroomIds = listOf()
    )

    @Provides
    @Singleton
    fun history() : History = History(0, listOf())
}