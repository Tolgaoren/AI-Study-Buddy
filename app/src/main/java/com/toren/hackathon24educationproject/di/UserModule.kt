package com.toren.hackathon24educationproject.di

import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Grade
import com.toren.hackathon24educationproject.domain.model.School
import com.toren.hackathon24educationproject.domain.model.Student
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideStudent() : Student = Student(
        id = "",
        fullName = "",
        classroom = Classroom(
            id = 0,
            name = "",
            students = listOf(),
            teachers = listOf(),
            subjects = listOf(),
            school = School(
                id = 0,
                name = "",
                city = "",
            ),
            grade = Grade.GRADE_1
        ),
        level = 0,
        badges = listOf(),
        historyId = 0
    )

    @Provides
    @Singleton
    fun provideClassroom() : Classroom = Classroom(
        id = 0,
        name = "",
        students = listOf(),
        teachers = listOf(),
        subjects = listOf(),
        school = School(
            id = 0,
            name = "",
            city = "",
        ),
        grade = Grade.GRADE_1
    )
}