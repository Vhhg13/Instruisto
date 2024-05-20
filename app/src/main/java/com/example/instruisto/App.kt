package com.example.instruisto

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {
    companion object{
        const val SHARED_PREF_NAME = "Instruisto"
    }
    var jwt: String? = null
        set(value) {
            getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit {
                putString("JWT", value)
            }
            field = value
        }
    var username: String? = null
        set(value) {
            getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit {
                putString("username", value)
            }
            field = value
        }
    override fun onCreate() {
        val sp = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        sp.edit {
            if(!sp.contains("isNight"))
                putBoolean("isNight", false)
        }
        jwt = sp.getString("JWT", "")
        username = sp.getString("username", "")
        super.onCreate()
    }
}