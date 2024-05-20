package com.example.instruisto.ui.profile

data class ProfileState(
    val username: String = "Login",
    val progress: Int = 0,
    val plan: Triple<Int, Int, Int> = Triple(10, 3, 28)
)
