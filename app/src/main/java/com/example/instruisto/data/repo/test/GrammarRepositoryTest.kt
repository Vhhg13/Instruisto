package com.example.instruisto.data.repo.test

import com.example.instruisto.data.repo.GrammarRepository
import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.util.Result

class GrammarRepositoryTest: GrammarRepository {
    override suspend fun all(): List<GrammarPoint> = grammar.map { it.copy(description = "") }

    override suspend fun byId(pointId: Int): Result<GrammarPoint> {
        val point = grammar.find { it.id == pointId }
        return when(point){
            null -> Result.NoSuchId(pointId)
            else -> Result.Success(point)
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