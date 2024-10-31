package com.toren.hackathon24educationproject.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.toren.hackathon24educationproject.domain.model.Classroom
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Student
import com.toren.hackathon24educationproject.domain.model.Teacher
import com.toren.hackathon24educationproject.domain.repository.AuthRepository
import com.toren.hackathon24educationproject.domain.repository.FirestoreRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository,
    private val student: Student,
    private val classroom: Classroom,
    private val teacher: Teacher
) : FirestoreRepository {

    override suspend fun saveStudent(): Resource<Boolean> {
        try {
            firestore
                .collection("students")
                .document(student.id)
                .set(student)
            return Resource.Success(true)

        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getStudent(): Resource<Student> {
        try {
            val document = firestore
                .collection("students")
                .document(authRepository.getUserUid())
                .get()
                .await()
            val data = document.toObject<Student>()
            Log.d("Student", data.toString())
            return Resource.Success(data!!)

        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }
/*
    override suspend fun getStudents(): Resource<List<Student>> {
        try {
            val document = firestore.collection("students")
                .document(student.classroomId)
                .collection("students")
                .document(student.id)
                .get()
            val data = docu
            return Resource.Success(data)

        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }*/

    override suspend fun updateStudent(): Resource<Boolean> {
        try {
            firestore
                .collection("students")
                .document(student.id)
                .set(student)
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun createClassroom(): Resource<Boolean> {
        try {
            firestore.collection("classrooms")
                .document(classroom.id)
                .set(classroom)
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getClassroom(): Resource<Classroom> {
        try {
            val document = firestore.collection("classrooms")
                .document(classroom.id)
                .get()
                .await()
            val data = document.toObject<Classroom>()
            return Resource.Success(data!!)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun updateClassroom(): Resource<Boolean> {
        try {
            firestore.collection("classrooms")
                .document(classroom.id)
                .set(classroom)
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun saveTeacher(): Resource<Boolean> {
        try {
            firestore.collection("teachers")
                .document(teacher.id)
                .set(teacher)
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun getTeacher(): Resource<Teacher> {
        try {
            val document = firestore.collection("teachers")
                .document(teacher.id)
                .get()
                .await()
            val data = document.toObject<Teacher>()
            Log.d("Teacher", data.toString())
            return Resource.Success(data!!)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }

    override suspend fun updateTeacher(): Resource<Boolean> {
        try {
            firestore.collection("teachers")
                .document(teacher.id)
                .set(teacher)
            return Resource.Success(true)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: "Unknown error occurred")
        }
    }


}