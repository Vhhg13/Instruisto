package com.example.instruisto.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Serializable
data class Deck(val id: Int, val name: String, val plan: String, val flashcards: List<Flashcard>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Flashcard)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(plan)
        parcel.writeTypedList(flashcards)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Deck> {
        override fun createFromParcel(parcel: Parcel): Deck {
            return Deck(parcel)
        }

        override fun newArray(size: Int): Array<Deck?> {
            return arrayOfNulls(size)
        }
    }
}