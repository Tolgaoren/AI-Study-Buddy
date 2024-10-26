package com.toren.hackathon24educationproject.domain.model

data class Teacher(
    val id: Int = 0,
    val fullName: String = "",
    val classrooms: List<Classroom> = listOf(),
    val school: School = School(),
)