package com.toren.hackathon24educationproject.domain.model

data class Classroom(
    val id: Int = 0,
    val name: String = "",
    val students: List<Student> = listOf(),
    val teachers: List<Teacher> = listOf(),
    val subjects: List<Subject> = listOf(),
    val school: School = School(),
    val grade: Grade = Grade.GRADE_1
)

