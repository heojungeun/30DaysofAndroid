package com.example.gallery

import android.os.Parcel
import android.os.Parcelable

data class Albums(var folderNames: String, var imagePath: String, var imgCount: Int, var isVideo: Boolean) :
    Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()){}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(folderNames)
        dest.writeString(imagePath)
        dest.writeInt(imgCount)
        dest.writeByte(if (isVideo) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Albums>{
        override fun createFromParcel(source: Parcel): Albums {
            return Albums(source)
        }

        override fun newArray(size: Int): Array<Albums?> {
            return arrayOfNulls(size)
        }
    }

}