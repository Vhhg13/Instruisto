package com.example.instruisto.data.repo.test

import com.example.instruisto.data.repo.GrammarRepository
import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.util.Result

class GrammarRepositoryTest: GrammarRepository {
    override suspend fun all(): List<GrammarPoint> = grammar.map { it.copy(description = "") }

    override suspend fun byId(point: GrammarPoint): Result<GrammarPoint> {
        val actualPoint = grammar.find { it.id == point.id }
        return when(actualPoint){
            null -> Result.NoSuchId(point.id)
            else -> Result.Success(actualPoint)
        }
    }

    private val grammar = mutableListOf(
        GrammarPoint(
            id = 1,
            name = "Nouns",
            description = "",
        ),
        GrammarPoint(
            id = 2,
            name = "",
            description = "",
        ),
        GrammarPoint(
            id = 3,
            name = "",
            description = "",
        ),
        GrammarPoint(
            id = 4,
            name = "",
            description = "",
        ),
    )
}