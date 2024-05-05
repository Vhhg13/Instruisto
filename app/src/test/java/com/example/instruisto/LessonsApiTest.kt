package com.example.instruisto

import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.model.AuthRequest
import junit.framework.TestCase.assertTrue
import com.example.instruisto.model.Lesson
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.random.Random
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class LessonsApiTest {

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

    private suspend fun signIn(): AuthRequest{
        val req = AuthRequest(AuthApiTest.sampleUser, AuthApiTest.samplePassword)
        token = api.login(req).body()
        return req
    }

    @Test
    fun `should NOT be able to get Lessons list without JWT`(){
        runBlocking {
            return@runBlocking
            // Arrange
            token = null
            // Act
            val response = api.getLessons()
            // Assert
            assertTrue("Expected code: 401 (no jwt), but got: ${response.code()}", response.code() == 401)
        }
    }

    @Test
    fun `should be able to retrieve a lesson by Id`(){
        runBlocking {
            return@runBlocking
            // Arrange
            signIn()
            val lesson: JsonObject = api.getLessons().body()!!.jsonArray[0].jsonObject
            val id: Int = Json.decodeFromJsonElement<Int>(lesson[Lesson.id]!!)
            val mandatoryFields = listOf("id", "passed", "exercises", "grammarPoints", "order")

            // Act
            val response: Response<JsonElement> = api.getLessonById(id)

            // Assert
            val json = response.body()!!.jsonObject
            assertTrue("LessonById JSON contains extra fields", json.keys.size==mandatoryFields.size)
            assertTrue("Lesson does not contain all mandatory fields", mandatoryFields.all {
                json.containsKey(it)
            })
        }
    }

    @Test
    fun `should NOT be able to retrieve a lesson by Id without JWT`(){
        runBlocking {
            return@runBlocking
            // Arrange
            token = null
            // Act
            val response = api.getLessonById(1)
            // Assert
            assertTrue("Expected code: 401 (no jwt), bot got: ${response.code()}", response.code() == 401)
        }
    }

    @Test
    fun `lesson retrieved by id should contain exercises`(){
        runBlocking {
            return@runBlocking
            signIn()
            // Arrange
            val lesson: JsonObject = api.getLessons().body()!!.jsonArray[0].jsonObject
            val id: Int = Json.decodeFromJsonElement<Int>(lesson[Lesson.id]!!)
            val lessonGotById: JsonObject = api.getLessonById(id).body()!!.jsonObject
            val mandatoryFields = listOf("id", "type", "question_text", "answer_text", "answer_audio")

            // Act
            val exercises: JsonObject = lessonGotById[Lesson.exercises]!!.jsonArray[0].jsonObject

            // Assert
            assertTrue("Exercise in Lesson json contains extra fields", exercises.keys.size==mandatoryFields.size)
            assertTrue("Exercise in Lesson json does not contain all the mandatory fields", mandatoryFields.all {
                exercises.containsKey(it)
            })
        }
    }

    @Test
    fun `lesson status should be changed`(){
        runBlocking {
            return@runBlocking
            // Arrange
            signIn()
            val lesson: JsonObject = api.getLessons().body()!!.jsonArray[0].jsonObject
            val id: Int = Json.decodeFromJsonElement<Int>(lesson[Lesson.id]!!)
            val oldStatus: Boolean = Json.decodeFromJsonElement<Boolean>(lesson[Lesson.status]!!)

            // Act
            val response = api.changeLessonStatus(id, Lesson.passed)

            // Assert
            val newLesson: JsonObject = api.getLessonById(id).body()!!.jsonObject
            val newStatus: Boolean = Json.decodeFromJsonElement<Boolean>(newLesson[Lesson.status]!!)
            assertTrue("Lesson status did not change", newStatus.xor(oldStatus))
            assertTrue("Lesson status did not change (${response.code()})", response.code() == 200)
        }
    }

    @Test
    fun `should NOT be able to change lesson status without JWT`(){
        runBlocking {
            return@runBlocking
            // Arrange
            token = null
            // Act
            val response = api.changeLessonStatus(1, "not passed")
            // Assert
            assertTrue("When patching lesson without JWT the code was ${response.code()} instead of 401", response.code() == 401)
        }
    }


}