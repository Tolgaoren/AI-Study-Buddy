package com.toren.hackathon24educationproject.domain.model

data class Student(
    val id: String = "",
    val fullName: String = "",
    val classroom: Classroom = Classroom(),
    val level: Int = 1,
    val badges: List<Badge> = listOf(),
    val historyId: Int = 0
)
