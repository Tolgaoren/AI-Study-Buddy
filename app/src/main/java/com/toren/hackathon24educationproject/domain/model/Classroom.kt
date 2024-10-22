package com.toren.hackathon24educationproject.domain.model

data class Classroom(
    val id: Int,
    val name: String,
    val students: List<Student>,
    val teachers: List<Teacher>,
    val subjects: List<Subject>,
    val school: School,
    val grade: Grade
)

