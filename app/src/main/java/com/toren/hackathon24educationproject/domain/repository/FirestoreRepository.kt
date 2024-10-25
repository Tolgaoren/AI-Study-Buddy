package com.toren.hackathon24educationproject.domain.repository

import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student

interface FirestoreRepository {

    suspend fun saveStudent(): Resource<Boolean>

    suspend fun getStudent(): Resource<Student>

    suspend fun getStudents(): Resource<List<Student>>

    suspend fun updateStudent(): Resource<Boolean>
}