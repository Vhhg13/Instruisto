package com.example.instruisto.di

import android.content.Context
import com.example.instruisto.App
import com.example.instruisto.data.datasource.InstruistoApi
import com.example.instruisto.data.repo.AuthRepository
import com.example.instruisto.data.repo.GrammarRepository
import com.example.instruisto.data.repo.LessonRepository
import com.example.instruisto.data.repo.impl.AuthRepositoryImpl
import com.example.instruisto.data.repo.impl.GrammarRepositoryImpl
import com.example.instruisto.data.repo.impl.LessonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideLessonRepository(api: InstruistoApi): LessonRepository =
        LessonRepositoryImpl(api)
    @Provides
    fun provideGrammarRepository(api: InstruistoApi): GrammarRepository =
        GrammarRepositoryImpl(api)

    @Provides
    fun provideApiService(interceptor: Interceptor): InstruistoApi {
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(InstruistoApi.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build().create(InstruistoApi::class.java)
    }

    @Provides
    fun provideAuthInterceptor(@ApplicationContext app: Context) = Interceptor { chain ->
        val builder = chain.request().newBuilder()
        val token = (app as App).jwt
        if(token != null)
            builder.addHeader("Authorization", "Bearer $token")
        chain.proceed(builder.build())
    }

    @Provides
    fun provideAuthRepo(api: InstruistoApi, @ApplicationContext app: Context): AuthRepository =
        AuthRepositoryImpl(api, (app as App))
}