package com.toren.hackathon24educationproject.domain.repository

import com.toren.hackathon24educationproject.domain.model.Resource

interface GeminiRepository {
    suspend fun startChat(subject: String): Resource<String>

    suspend fun checkAnswer(answer: String): Resource<String>
}