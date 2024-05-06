package com.example.instruisto.ui.lesson

import com.example.instruisto.model.Exercise
import com.example.instruisto.model.Lesson

data class LessonState(
    val step: Lesson.Step = Exercise(
        id = -69,
        type = Exercise.Type.TRANSLATION,
        questionText = "",
        answerText = "",
        audioUrl = null
    ),
    val progress: Int = 0,
    val isAnswerCorrect: Boolean? = null,
    val correctAnswer: String? = null
)