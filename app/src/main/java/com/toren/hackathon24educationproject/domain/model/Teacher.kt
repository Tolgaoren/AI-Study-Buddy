package com.toren.hackathon24educationproject.domain.model

data class Teacher(
    var id: String = "",
    var fullName: String = "",
    var classroomIds: List<String> = listOf()
){
    fun reset() {
        id = ""
        fullName = ""
        classroomIds = listOf()
    }
}