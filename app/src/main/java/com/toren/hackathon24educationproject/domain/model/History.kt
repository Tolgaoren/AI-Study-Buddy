package com.toren.hackathon24educationproject.domain.model

import com.google.ai.client.generativeai.type.Content

data class History(
    val id: Int,
    val history: List<Content>,
)
