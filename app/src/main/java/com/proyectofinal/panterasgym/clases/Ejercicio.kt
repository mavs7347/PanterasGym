package com.proyectofinal.panterasgym.clases

import java.io.Serializable

class Ejercicio : Serializable {
    var eNombre: String = ""
        get() = field
        set(value) { field = value }

    var eDescripcion: String = ""
        get() = field
        set(value) { field = value }

    var eRepeticiones: Int = 0
        get() = field
        set(value) { field = value }

    var ePeso: Float = 0.0f
        get() = field
        set(value) { field = value }

    var eSeries: Int = 0
        get() = field
        set(value) { field = value }
}