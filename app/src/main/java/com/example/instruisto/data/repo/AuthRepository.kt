package com.example.instruisto.data.repo

interface AuthRepository {
    suspend fun register(username: String, password: String): Boolean
    suspend fun login(username: String, password: String): Boolean
    suspend fun logout()
}