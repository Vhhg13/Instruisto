package com.example.instruisto.model

import android.os.Parcel
import android.os.Parcelable

data class GrammarPoint(val id: Int, val name: String, val description: String) : Lesson.Step,
    Parcelable {
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