package com.toren.hackathon24educationproject.domain.model

data class Subject(
    val id: Int,
    val name: String,
    val teachers: List<Teacher>,
    val students: List<Student>,
    val classroom: List<Classroom>
)
