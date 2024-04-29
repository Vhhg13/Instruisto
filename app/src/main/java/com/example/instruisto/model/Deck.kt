package com.example.instruisto.model

import android.os.Parcel
import android.os.Parcelable

data class Deck(val id: Long, val name: String, val plan: String, val flashcards: List<Flashcard>) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(Flashcard)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
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