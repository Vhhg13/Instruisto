package com.example.instruisto.di

import com.example.instruisto.data.repo.GrammarRepository
import com.example.instruisto.data.repo.LessonRepository
import com.example.instruisto.data.repo.test.GrammarRepositoryTest
import com.example.instruisto.data.repo.test.LessonRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideLessonRepository(): LessonRepository{
        return LessonRepositoryTest()
    }
    @Provides
    fun provideGrammarRepository(): GrammarRepository{
        return GrammarRepositoryTest()
    }
}