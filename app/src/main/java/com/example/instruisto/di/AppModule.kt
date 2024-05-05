package com.example.instruisto.di

import com.example.instruisto.model.AuthRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideSth(): AuthRequest{
        return AuthRequest("a", "b")
    }
}