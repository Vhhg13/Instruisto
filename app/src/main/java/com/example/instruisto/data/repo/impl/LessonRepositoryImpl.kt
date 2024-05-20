package com.example.instruisto.data.repo.impl

import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.data.repo.LessonRepository
import com.example.instruisto.model.Exercise
import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.model.Lesson
import com.example.instruisto.util.Result
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import javax.inject.Inject

class LessonRepositoryImpl @Inject constructor(private val api: InstruistoApi): LessonRepository {
    override suspend fun byId(id: Int): Result<Lesson> {
        val response = api.getLessonById(id)
        val lesson = response.body()!!.jsonObject
        val isAvailable = true

        val exercises: Map<Int, Exercise> = lesson[Lesson.exercises]!!.jsonArray.associateBy(
            keySelector = {
                Json.decodeFromJsonElement(it.jsonObject[Exercise.id]!!)
            },
            valueTransform = {
                Json.decodeFromJsonElement(it)
            }
        )
        val grammarPoints: Map<Int, GrammarPoint> =
            lesson[Lesson.grammarPoints]!!.jsonArray.associateBy(
                keySelector = {
                    Json.decodeFromJsonElement(it.jsonObject[GrammarPoint.id]!!)
                },
                valueTransform = {
                    Json.decodeFromJsonElement(it)
                }
            )

        val steps: List<Lesson.Step> = lesson[Lesson.order]!!.jsonArray.map {
            val orderId: Int = Json.decodeFromJsonElement(it)
            if (exercises.containsKey(orderId))
                exercises[orderId]!!
            else
                grammarPoints[orderId]!!
        }

        return Result.Success(
            Lesson(
                id = id,
                status = isAvailable,
                number = Json.decodeFromJsonElement(lesson[Lesson.number]!!),
                steps = steps
            )
        )
    }

    override suspend fun all(): List<Lesson> {
        val response = api.getLessons()
        if (!response.isSuccessful) throw Exception("Unauthorized")
        var index = 1
        return buildList {
            response.body()!!.jsonArray.forEach {
                add(
                    Lesson(
                        id = Json.decodeFromJsonElement(it.jsonObject[Lesson.number]!!),
                        status = Json.decodeFromJsonElement(it.jsonObject[Lesson.isAvailable]!!),
                        number = index++,
                        steps = listOf()
                    )
                )
            }
        }
    }

    override suspend fun study(id: Int, status: Boolean): Result<Unit> {
        val response = api.changeLessonStatus(id, if (status) "passed" else "not passed")
        return if (response.isSuccessful)
            Result.Success(Unit)
        else
            Result.NoSuchId(id)
    }

    override suspend fun progress(): Int {
        var available = -1
        api.getLessons().body()!!.jsonArray.forEach {
            available += Json.decodeFromJsonElement<Boolean>(it.jsonObject[Lesson.isAvailable]!!).compareTo(false)
        }
        return available
    }
}