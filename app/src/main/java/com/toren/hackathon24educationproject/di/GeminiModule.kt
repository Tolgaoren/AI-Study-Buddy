package com.toren.hackathon24educationproject.di

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.toren.hackathon24educationproject.BuildConfig
import com.toren.hackathon24educationproject.data.repository.GeminiRepositoryImpl
import com.toren.hackathon24educationproject.domain.repository.GeminiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeminiModule {

    @Provides
    @Singleton
    fun generativeModel(): GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.API_KEY,
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.LOW_AND_ABOVE),
        )
    )

    @Provides
    @Singleton
    fun provideGeminiRepository(
        generativeModel: GenerativeModel
    ): GeminiRepository = GeminiRepositoryImpl(generativeModel)


}