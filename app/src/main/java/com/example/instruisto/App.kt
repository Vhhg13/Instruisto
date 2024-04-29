package com.example.instruisto

import android.app.Application
import android.content.Context
import androidx.core.content.edit

class App: Application() {
    companion object{
        const val SHARED_PREF_NAME = "Instruisto"
    }
    override fun onCreate() {
        val sp = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        sp.edit {
            if(!sp.contains("isNight"))
                putBoolean("isNight", false)
        }
        super.onCreate()
    }
}