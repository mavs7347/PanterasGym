package com.proyectofinal.panterasgym.clases

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Rutina(
    var rNombre: String = "",
    var rDescripcion: String = "",
    var rCreacion: String = "",
    var rDuracion: String = "",
    var rEjercicios: List<Ejercicio> = listOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        mutableListOf<Ejercicio>().apply {
            parcel.readList(this as List<*>, Ejercicio::class.java.classLoader)
        }
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(rNombre)
        parcel.writeString(rDescripcion)
        parcel.writeString(rCreacion)
        parcel.writeString(rDuracion)
        parcel.writeList(rEjercicios)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rutina> {
        override fun createFromParcel(parcel: Parcel): Rutina {
            return Rutina(parcel)
        }

        override fun newArray(size: Int): Array<Rutina?> {
            return arrayOfNulls(size)
        }
    }
}