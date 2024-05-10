package com.example.instruisto.ui.lesson

import com.example.instruisto.model.Exercise
import com.example.instruisto.model.GrammarPoint

sealed interface LessonState {
    data object ErrorLessonState : LessonState

    data object StartLessonState : LessonState

    data class ExerciseLessonState(val exercise: Exercise) : LessonState
    data object CorrectLessonState : LessonState
    data class IncorrectLessonState(val correctAnswer: String) : LessonState

    data class GrammarLessonState(val grammarPoint: GrammarPoint) : LessonState

    data class EndLessonState(val performance: Pair<Int, Int>) : LessonState
}