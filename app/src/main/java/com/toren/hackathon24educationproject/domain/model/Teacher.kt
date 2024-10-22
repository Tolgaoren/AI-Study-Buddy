package com.toren.hackathon24educationproject.domain.model

data class Teacher(
    val id: Int,
    val fullName: String,
    val classrooms: List<Classroom>,
    val school: School,
)