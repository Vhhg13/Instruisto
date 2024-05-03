package com.example.instruisto

import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.model.AuthRequest
import junit.framework.TestCase.assertTrue
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

class AuthApiTest {
    companion object{
        const val randomLength = 10
        const val sampleUser = "sampleUser"
        const val samplePassword = "samplePassword"
    }
    private fun randomString(len: Int) =
        (0..len).map { Random.nextInt('a'.code, 'z'.code) }.joinToString("")
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

    @Test
    fun `logging in with invalid credentials should throw 401`() {
        runBlocking {
            //Arrange
            val username = randomString(randomLength)
            val password = randomString(randomLength)
            val req = AuthRequest(username, password)
            // Act
            val response: Response<String> = api.login(req)
            // Assert
            assertTrue("Expected: 401, got: ${response.code()}", response.code() == 401)
        }
    }

    @Test
    fun `registering twice should throw 409`(){
        runBlocking {
            // Arrange
            val username = randomString(randomLength)
            val password = randomString(randomLength)
            val req = AuthRequest(username, password)
            api.register(req)

            // Act
            val response: Response<Unit> = api.register(req)

            // Assert
            assertTrue("Expected: 409, got: ${response.code()}", response.code() == 409)
        }
    }

    @Test
    fun `unsuccessful log in, then successful registration should let the user log in and get a jwt`(){
        runBlocking {
            // Arrange
            val username = randomString(randomLength)
            val password = randomString(randomLength)
            val req = AuthRequest(username, password)

            val loginResponse: Response<String> = api.login(req)
            assertTrue("1. Expected: 401, got: ${loginResponse.code()}", loginResponse.code() == 401)
            api.register(req)

            // Act
            val loginResponse2: Response<String> = api.login(req)

            // Assert
            assertTrue("3. Got ${loginResponse2.code()}", loginResponse2.code() == 200)
            assertTrue("2. Expected: eyJwt, got: ${loginResponse2.body()}", loginResponse2.body()!!.startsWith("eyJ"))
        }
    }

    @Test
    fun `password change should succeed`(){
        runBlocking {
            // Arrange
            val username = randomString(randomLength)
            val password = randomString(randomLength)
            val req = AuthRequest(username, password)
            api.register(req)
            token = api.login(req).body()
            val newPassword = randomString(randomLength)

            // Act
            api.changePassword(newPassword)
            val response = api.login(AuthRequest(username, newPassword))

            // Assert
            assertTrue("Password change did not succeed (${response.code()})", response.code() == 200)
        }
    }

    @Test
    fun `password change without JWT should fail`(){
        runBlocking {
            // Arrange
            token = null
            // Act
            val response = api.changePassword("new Passlaskjlaskdjlaskjlksjajdlasj")
            // Assert
            assertTrue("Password change without JWT succeeded", response.code() == 401)
        }
    }

    @Test
    fun `should be able to retrieve all lessons without JWT`(){
        runBlocking {
            // Arrange
            // Act
            val response = api.getLessons()
            // Assert
            assertTrue("Wasn't able to retrieve all lessons without JWT (${response.code()})", response.code() == 200)
        }
    }
    private suspend fun registerRandomUser(): AuthRequest{
        val req = AuthRequest(randomString(randomLength), randomString(randomLength))
        api.register(req)
        return req
    }
    @Test
    fun `should be able to retrieve all lessons with a JWT`(){
        runBlocking {
            // Arrange
            val req = registerRandomUser()
            token = api.login(req).body()
            assertTrue("Token turned out to be null", token == null)
            // Act
            val response = api.getLessons()
            // Assert
            assertTrue("Wasn't able to retrieve all lessons with a JWT (${response.code()})", response.code() == 200)
        }
    }
}