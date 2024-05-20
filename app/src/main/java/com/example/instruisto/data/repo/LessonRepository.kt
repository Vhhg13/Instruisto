package com.example.instruisto.data.repo

import com.example.instruisto.model.Lesson
import com.example.instruisto.util.Result

interface LessonRepository {
    suspend fun byId(id: Int): Result<Lesson>
    suspend fun all(): List<Lesson>
    suspend fun study(id: Int, status: Boolean): Result<Unit>

    suspend fun progress(): Int
}