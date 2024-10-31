package com.toren.hackathon24educationproject.domain.model

data class Student(
    var id: String = "",
    var fullName: String = "",
    var classroomId: String = "",
    var level: Int = 1250,
    var badges: List<Badge> = listOf(),
    var historyId: Int = 0
)
