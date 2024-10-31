package com.toren.hackathon24educationproject.domain.repository

import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.model.Teacher

interface FirestoreRepository {

    suspend fun saveStudent(): Resource<Boolean>

    suspend fun getStudent(): Resource<Student>

  //  suspend fun getStudents(): Resource<List<Student>>

    suspend fun updateStudent(): Resource<Boolean>

    suspend fun createClassroom(): Resource<Boolean>

    suspend fun getClassroom(): Resource<Classroom>

    suspend fun updateClassroom(): Resource<Boolean>

    suspend fun saveTeacher(): Resource<Boolean>

    suspend fun getTeacher(): Resource<Teacher>

    suspend fun updateTeacher(): Resource<Boolean>
}