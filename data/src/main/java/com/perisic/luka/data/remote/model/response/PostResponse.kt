package com.perisic.luka.data.remote.model.response

import android.os.Parcel
import android.os.Parcelable
import com.perisic.luka.data.remote.model.helper.User

data class PostResponse(
    val user: User,
    val id: Int,
    val title: String,
    val description: String,
    val locationString: String?,
    val noLocation: Int,
    val priceAmount: Float,
    val priceCurrency: String,
    val priceMode: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(User::class.java.classLoader)
            ?: throw Exception("Something went wrong"),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(locationString)
        parcel.writeInt(noLocation)
        parcel.writeFloat(priceAmount)
        parcel.writeString(priceCurrency)
        parcel.writeString(priceMode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostResponse> {
        override fun createFromParcel(parcel: Parcel): PostResponse {
            return PostResponse(parcel)
        }

        override fun newArray(size: Int): Array<PostResponse?> {
            return arrayOfNulls(size)
        }
    }
}