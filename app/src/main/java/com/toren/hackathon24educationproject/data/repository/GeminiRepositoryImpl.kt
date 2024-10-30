package com.toren.hackathon24educationproject.data.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.toren.hackathon24educationproject.domain.model.History
import com.toren.hackathon24educationproject.domain.model.Resource
import com.toren.hackathon24educationproject.domain.model.Role
import com.toren.hackathon24educationproject.domain.repository.GeminiRepository
import com.toren.hackathon24educationproject.util.PromptGenerator
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel,
    private var history: History,
    private val promptGenerator: PromptGenerator
): GeminiRepository {

    override suspend fun startChat(subject: String): Resource<String> {
        return try {
            val prompt = promptGenerator.generatePrompt(subject)
            val chat = Resource.Success(
                generativeModel.startChat(
                    history = history.history
                ).sendMessage(prompt).text.toString()
            )
            val newContent = listOf(
                Content("USER", listOf(TextPart(prompt)) ),
                Content("MODEL", listOf(TextPart(chat.message ?: ""))))

            val updatedHistory = history.copy(
                history = history.history + newContent
            )
            history = updatedHistory

            return chat
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }
}