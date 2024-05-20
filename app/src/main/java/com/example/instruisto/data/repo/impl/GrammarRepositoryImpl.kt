package com.example.instruisto.data.repo.impl

import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.data.repo.GrammarRepository
import com.example.instruisto.model.GrammarPoint
import com.example.instruisto.util.Result
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import javax.inject.Inject

class GrammarRepositoryImpl @Inject constructor(private val api: InstruistoApi): GrammarRepository {
    override suspend fun all(): List<GrammarPoint> {
        val response = api.getGrammar()
        if(response.code() == 401) throw Exception("Unauthorized")
        if(!response.isSuccessful) throw Exception(response.code().toString())
        val body = response.body()!!
        return buildList {
            body.jsonArray.forEach {
                val obj = it.jsonObject
                add(GrammarPoint(
                    id = Json.decodeFromJsonElement(obj[GrammarPoint.id]!!),
                    name = Json.decodeFromJsonElement(obj[GrammarPoint.name]!!),
                    description = Json.decodeFromJsonElement(obj[GrammarPoint.description]!!)
                ))
            }
        }
    }

    override suspend fun byId(point: GrammarPoint): Result<GrammarPoint> {
        val response = api.getGrammarPoint(point.id)
        if(response.code() == 401) throw Exception("Unauthorized")
        if(!response.isSuccessful) throw Exception(response.code().toString())
        return if(response.code() == 404){
            Result.NoSuchId(point.id)
        }else{
            Result.Success(point.copy(description = response.body()!!))
        }
    }
}