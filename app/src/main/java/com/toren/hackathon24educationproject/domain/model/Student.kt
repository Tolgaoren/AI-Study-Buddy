package com.toren.hackathon24educationproject.domain.model

data class Student(
    var id: String = "",
    var fullName: String = "",
    var classroomId: String = "",
    var level: Int = 0,
    var badges: List<Int> = listOf(),
    var historyId: Int = 0,
    var avatar: Int = 1
){
    fun reset() {
        id = ""
        fullName = ""
        classroomId = ""
        level = 0
        badges = listOf()
        historyId = 0
        avatar = 1
    }
}

