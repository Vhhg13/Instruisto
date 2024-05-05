package com.example.instruisto.model

import android.os.Parcel
import android.os.Parcelable
import com.example.instruisto.util.DateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Flashcard(
    val id: Int,
    val front: String,
    val back: String,
    @Serializable(with = DateSerializer::class) val nextReview: Date,
    val imageUrl: String,
    val deck: Long
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        Date(parcel.readLong()),
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(front)
        parcel.writeString(back)
        parcel.writeLong(nextReview.time)
        parcel.writeString(imageUrl)
        parcel.writeLong(deck)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Flashcard> {
        override fun createFromParcel(parcel: Parcel): Flashcard {
            return Flashcard(parcel)
        }

        override fun newArray(size: Int): Array<Flashcard?> {
            return arrayOfNulls(size)
        }
    }

}
