package com.example.instruisto.data.repo.impl

import android.content.Context
import com.example.instruisto.App
import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.data.repo.AuthRepository
import com.example.instruisto.model.AuthRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val api: InstruistoApi, private val app: App) : AuthRepository {
    override suspend fun register(username: String, password: String): Boolean {
        val response = api.register(AuthRequest(username, password))
        if (response.code() == 409) return false
        if (response.code() == 201) return login(username, password)
        throw Exception("Invalid json, ${response.code()}")
    }

    override suspend fun login(username: String, password: String): Boolean {
        val response = api.login(AuthRequest(username, password))
        if (response.code() == 200){
            app.jwt = response.body()
            app.username = username
            return true
        }
        if (response.code() == 401) return false
        throw Exception("Invalid json, ${response.code()}")
    }

    override suspend fun logout() {
        app.jwt = null
        app.username = null
    }


}