package com.turastory.simpleapp.vo

import android.os.Parcel
import android.os.Parcelable
import com.turastory.simpleapp.util.ViewType

data class Post(val id: Int,
                val userId: Int,
                val title: String,
                val body: String) : ViewType, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "")

    override fun getViewType(): Int {
        return ViewType.CONTENT
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeString(title)
        parcel.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}