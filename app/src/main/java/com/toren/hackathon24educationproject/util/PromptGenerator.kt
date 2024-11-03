package com.toren.hackathon24educationproject.util

import com.toren.hackathon24educationproject.domain.model.Classroom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptGenerator @Inject constructor(
    private val classroom: Classroom,
) {
    fun generatePrompt(subject: String) : String {
        val prompt = "Bana $subject konusunu pekiştirmem için, ${classroom.grade} sınıf seviyesinde bir soru sor. Soru metnini oluştururken bana, işte sorun gibi cümleler kurma direkt soruyu sor. Daha önce sormadığın bir soru olsun. Soru, daha önce sorduğun sorulara göre bir seviye daha zor olabilir."
        println(prompt)
        return prompt
    }

    fun checkAnswerPrompt(answer: String) : String {
        return "Son sorduğun soruya cevabım $answer. Bana cevap doğruysa doğru, yanlışsa yanlış yaz ve bana açıklamayı söyle. Hemen hemen doğru vs. deme. Daha sonra çözümü anlat."
    }

    fun getExplanation(subject: String) : String {
        return "$subject konusunda 4. sınıf seviyesinde konu anlatımı yap."
    }
}