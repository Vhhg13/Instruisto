package com.example.instruisto.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    val id: Int,
    val type: Type,
    val questionText: String,
    val answerText: String,
    val audio: String?
) : Lesson.Step {
    companion object{
        const val id = "id"
    }
    enum class Type{
        @SerialName("tra") TRANSLATION,
        @SerialName("sub") SUBSTITUTION,
        @SerialName("aud") LISTENING
    }
}