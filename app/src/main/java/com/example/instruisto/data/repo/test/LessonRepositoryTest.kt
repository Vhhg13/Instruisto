package com.example.instruisto.data.repo.test

import com.example.instruisto.data.repo.LessonRepository
import com.example.instruisto.model.Exercise
import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.model.Lesson
import com.example.instruisto.util.Result

class LessonRepositoryTest: LessonRepository {

    override suspend fun byId(id: Int): Result<Lesson> {
        val lesson = lessons.find { it.id == id }
        return when(lesson){
            null -> Result.NoSuchId(id)
            else -> Result.Success(lesson.copy(steps = buildList {
                addAll(exercises)
                add(GrammarPoint(1, "a", "b"))
                add(GrammarPoint(2, "c", "d"))
                add(GrammarPoint(3, "e", "f"))
            }.shuffled()))
        }
    }

    override suspend fun all() = lessons

    override suspend fun study(id: Int, status: Boolean): Result<Unit> {
        val index = lessons.indexOfFirst { it.id == id }
        return when(index){
            -1 -> Result.NoSuchId(id)
            else -> {
                lessons[index] = lessons[index].copy(status = true)
                Result.Success(Unit)
            }
        }
    }

    private val lessons = mutableListOf(
        Lesson(
            id = 1,
            number = 1,
            status = true,
            steps = listOf()
        ),
        Lesson(
            id = 2,
            number = 2,
            status = true,
            steps = listOf()
        ),
        Lesson(
            id = 3,
            number = 3,
            steps = listOf(),
            status = false
        ),
        Lesson(
            id = 4,
            number = 4,
            steps = listOf(),
            status = false
        ),
        Lesson(
            id = 5,
            number = 5,
            steps = listOf(),
            status = false
        ),
    )

    private val exercises = listOf(
        Exercise(
            id = 1,
            type = Exercise.Type.TRANSLATION,
            questionText = "Lingvo internacia",
            answerText = "International language",
            audioUrl = null
        ),
        Exercise(
            id = 2,
            type = Exercise.Type.SUBSTITUTION,
            questionText = "_ internacia",
            answerText = "Lingvo",
            audioUrl = null
        ),
        Exercise(
            id = 3,
            type = Exercise.Type.LISTENING,
            questionText = "",
            answerText = "Lingvo internacia",
            audioUrl = ""
        ),
        Exercise(
            id = 4,
            type = Exercise.Type.SUBSTITUTION,
            questionText = "Lingvo _",
            answerText = "internacia",
            audioUrl = ""
        ),
        Exercise(
            id = 5,
            type = Exercise.Type.TRANSLATION,
            questionText = "Lingvo internacia",
            answerText = "International language",
            audioUrl = ""
        ),
    )
}