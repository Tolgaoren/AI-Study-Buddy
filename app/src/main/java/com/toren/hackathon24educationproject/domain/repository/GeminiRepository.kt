package com.toren.hackathon24educationproject.domain.repository

import com.google.ai.client.generativeai.type.Content
import com.toren.hackathon24educationproject.domain.model.Resource

interface GeminiRepository {
    suspend fun startChat(prompt: String, history: List<Content>): Resource<String>
}