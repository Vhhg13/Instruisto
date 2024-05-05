package com.example.instruisto.model

data class Exercise(
    val id: Int,
    val type: Type,
    val questionText: String,
    val answerText: String,
    val audioUrl: String?
) : Lesson.Step {
    enum class Type{
        TRANSLATION,
        SUBSTITUTION,
        LISTENING
    }
}