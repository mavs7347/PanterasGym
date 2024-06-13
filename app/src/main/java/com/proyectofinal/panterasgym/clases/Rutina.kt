package com.proyectofinal.panterasgym.clases

import java.io.Serializable

class Rutina : Serializable {
    var rNombre: String = ""
        get() {return  field}
        set(value) { field = value }
    var rDescripcion: String = ""
        get() {return  field}
        set(value) { field = value }
    var rCreacion: String = ""
        get() {return  field}
        set(value) { field = value }
    var rDuracion: String = ""
        get() {return  field}
        set(value) { field = value }
    var rEjercicios: List<Ejercicio> = listOf()
        get() {return  field}
        set(value) { field = value }
}