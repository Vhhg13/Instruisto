package com.example.instruisto.data.repo

import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.util.Result

interface GrammarRepository {
    suspend fun all(): List<GrammarPoint>
    suspend fun byId(point: GrammarPoint): Result<GrammarPoint>
}