package com.toren.hackathon24educationproject.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.repository.GeminiRepository
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
): GeminiRepository {

    override suspend fun startChat(prompt: String, history: List<Content>): Resource<String> {
        return try {
            Resource.Success(
                generativeModel.startChat(
                    history = history
                ).sendMessage(prompt).text.toString()
            )
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}