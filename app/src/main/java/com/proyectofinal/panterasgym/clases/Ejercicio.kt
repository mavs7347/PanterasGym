package com.proyectofinal.panterasgym.clases

import android.os.Parcel
import android.os.Parcelable

data class Ejercicio(
    var eNombre: String = "",
    var eDescripcion: String = "",
    var eRepeticiones: Int = 0,
    var ePeso: Float = 0.0f,
    var eSeries: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(eNombre)
        parcel.writeString(eDescripcion)
        parcel.writeInt(eRepeticiones)
        parcel.writeFloat(ePeso)
        parcel.writeInt(eSeries)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ejercicio> {
        override fun createFromParcel(parcel: Parcel): Ejercicio {
            return Ejercicio(parcel)
        }

        override fun newArray(size: Int): Array<Ejercicio?> {
            return arrayOfNulls(size)
        }
    }
}