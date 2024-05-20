package com.example.instruisto.model

import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val id: Int,
    val number: Int,
    val steps: List<Step>,
    val status: Boolean
){
    companion object{
        const val id = "id"
        const val exercises = "exercises"
        const val isAvailable = "isAvailable"
        const val passed = "passed"
        const val notPassed = "not passed"
        const val status = "status"
        const val number = "number"
        const val order = "order"
        const val grammarPoints = "grammarPoints"
    }
    sealed interface Step
}
