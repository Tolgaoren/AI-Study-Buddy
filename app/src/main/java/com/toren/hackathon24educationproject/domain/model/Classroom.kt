package com.toren.hackathon24educationproject.domain.model

data class Classroom(
    val id: String = "",
    val name: String = "",
    val studentIds: List<String> = listOf(),
    val teacherIds: List<String> = listOf(),
    val subjects: List<String> = listOf(),
    val grade: Grade = Grade.GRADE_1
)

