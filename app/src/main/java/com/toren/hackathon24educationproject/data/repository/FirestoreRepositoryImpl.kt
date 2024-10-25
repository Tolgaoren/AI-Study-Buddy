package com.toren.hackathon24educationproject.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.repository.FirestoreRepository
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val student: Student
) : FirestoreRepository {


    override suspend fun saveStudent(): Resource<Boolean> {
        try {
            firestore.collection("students")
                .document(student.classroom.school.id.toString())
                .collection(student.classroom.id.toString())
                .document(student.id.toString())
                .set(student)
            return Resource.Success(true)

        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getStudent(): Resource<Student> {
        try {
            val document = firestore.collection("students")
                .document(student.classroom.school.id.toString())
                .collection(student.classroom.id.toString())
                .document(student.id.toString()).get()
            val data = document.result.toObject<Student>()
            return Resource.Success(data!!)

        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getStudents(): Resource<List<Student>> {
        try {
            val document = firestore.collection("students")
                .document(student.classroom.school.id.toString())
                .collection(student.classroom.id.toString())
                .get()
            val data = document.result.toObjects<Student>()
            return Resource.Success(data)

        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun updateStudent(): Resource<Boolean> {
        try {
            firestore.collection("students")
                .document(student.classroom.school.id.toString())
                .collection(student.classroom.id.toString())
                .document(student.id.toString())
                .set(student)
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }
}