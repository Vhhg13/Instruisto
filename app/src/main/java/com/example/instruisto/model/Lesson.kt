package com.example.instruisto.model

data class Lesson(
    val id: Int,
    val number: Int,
    val steps: List<Step>,
    val status: Boolean
){
    companion object{
        const val id = "id"
        const val exercises = "exercises"
        const val passed = "passed"
        const val notPassed = "not passed"
        const val status = "status"
    }
    sealed interface Step
    data object End : Step
}
