package com.toren.hackathon24educationproject.domain.model

data class Student(
    val id: Int,
    val fullName: String,
    val classroom: Classroom,
    val level: Int,
    val badges: List<Badge>,
    val historyId: Int
)
