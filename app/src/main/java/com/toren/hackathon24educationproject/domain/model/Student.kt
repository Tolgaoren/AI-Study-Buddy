package com.toren.hackathon24educationproject.domain.model

data class Student(
    val id: Int,
    val fullName: String,
    val grade: Grade,
    val classroom: Classroom,
    val school: School,
    val subjects: List<Subject>,
    val level: Int,
    val badges: List<Badge>
)
