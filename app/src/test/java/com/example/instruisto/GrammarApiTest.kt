package com.example.instruisto

import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.model.AuthRequest
import com.example.instruisto.model.Lesson
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.random.Random
import junit.framework.TestCase.assertTrue
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class GrammarApiTest {
    var token: String? = null
    val client = OkHttpClient.Builder().addInterceptor {
        val req = it.request().newBuilder()
        token?.let{req.addHeader("Authorization", "Bearer $token")}
        it.proceed(req.build())
    }.build()
    val rf = Retrofit.Builder().baseUrl("http://localhost:3197").client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json; charset=UTF8")))
        .build()

    val api = rf.create(InstruistoApi::class.java)

    val test = false

    @Test
    fun `should not be able to get the grammar list without JWT`(){
        runBlocking {
            if(!test) return@runBlocking
            // Arrange
            token = null
            // Act
            val response = api.getGrammar()
            // Assert
            assertTrue("Expected code: 401, got: ${response.code()}", response.code() == 401)
        }
    }
    private suspend fun signUp(): AuthRequest{
        val req = AuthRequest(AuthApiTest.sampleUser, AuthApiTest.samplePassword)
        api.register(req)
        token = api.login(req).body()
        return req
    }
    @Test
    fun `should be able to get the grammar list with a JWT`(){
        runBlocking {
            if(!test) return@runBlocking
            // Arrange
            signUp()
            // Act
            val response = api.getGrammar()
            // Assert
            assertTrue("code = ${response.code()}", response.code() == 200)
        }
    }

    @Test
    fun `should be able to retrieve grammarpoint by id`(){
        runBlocking {
            if(!test) return@runBlocking
            // Arrange
            signUp()
            // Act
            val response = api.getGrammarPoint(1)
            // Assert
            assertTrue("code = ${response.code()}", response.code() == 200)
        }
    }

    @Test
    fun `should NOT be able to retrieve a grammarpoint without JWT`(){
        runBlocking {
            if(!test) return@runBlocking
            // Arrange
            token = null
            // Act
            val response = api.getGrammarPoint(1)
            // Assert
            assertTrue("Expected code: 401 (no jwt), but got: ${response.code()}", response.code() == 401)
        }
    }

    @Test
    fun `should throw 404 when no point with given id`(){
        runBlocking {
            if(!test) return@runBlocking
            // Arrange
            signUp()
            // Act
            val response = api.getGrammarPoint(-1)
            // Assert
            assertTrue("${response.code()} ${response.body()}", response.code() == 404 && response.body() == "No such grammar point")
        }
    }
}