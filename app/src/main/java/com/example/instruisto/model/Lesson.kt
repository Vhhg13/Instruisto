package com.example.instruisto.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

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
    data class Exercise(
        val id: Int,
        val type: Type,
        val questionText: String,
        val answerText: String,
        val audioUrl: String?
    ) : Step{
        enum class Type{
            TRANSLATION,
            SUBSTITUTION,
            LISTENING
        }
    }
    data class GrammarPoint(val id: Int, val name: String, val description: String) : Step, Parcelable{
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(description)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<GrammarPoint> {
            const val id = "id"
            override fun createFromParcel(parcel: Parcel): GrammarPoint {
                return GrammarPoint(parcel)
            }

            override fun newArray(size: Int): Array<GrammarPoint?> {
                return arrayOfNulls(size)
            }
        }

    }
}
