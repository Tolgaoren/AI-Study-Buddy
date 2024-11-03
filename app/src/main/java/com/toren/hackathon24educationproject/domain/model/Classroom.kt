package com.toren.hackathon24educationproject.domain.model

data class Classroom(
    var id: String = "",
    var name: String = "",
    var studentIds: List<String> = listOf(),
    var teacherIds: List<String> = listOf(),
    var subjects: List<String> = listOf(),
    var grade: Int = 1
)

