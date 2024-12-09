package com.proyectofinal.panterasgym.clases

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
open class Cliente(
    var cNombre: String = "",
    var cCorreo: String = "",
    var cContrasena: String = "",
    var cEdad: Int = 0,
    var cPeso: Float = 0.0f,
    var cAltura: Float = 0.0f,
    var cRecordar: Boolean = false,
    var cRutinas: ArrayList<Rutina> = arrayListOf()
) : Parcelable {

    val cIndiceMasaCorporal: Float
        get() = if (cAltura > 0) cPeso / (cAltura * cAltura) else 0f

    constructor(parcel: Parcel) : this(
        cNombre = parcel.readString() ?: "",
        cCorreo = parcel.readString() ?: "",
        cContrasena = parcel.readString() ?: "",
        cEdad = parcel.readInt(),
        cPeso = parcel.readFloat(),
        cAltura = parcel.readFloat(),
        cRecordar = parcel.readByte() != 0.toByte(),
        cRutinas = parcel.createTypedArrayList(Rutina.CREATOR) ?: arrayListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cNombre)
        parcel.writeString(cCorreo)
        parcel.writeString(cContrasena)
        parcel.writeInt(cEdad)
        parcel.writeFloat(cPeso)
        parcel.writeFloat(cAltura)
        parcel.writeByte(if (cRecordar) 1 else 0)
        parcel.writeTypedList(cRutinas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }
}