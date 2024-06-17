package com.proyectofinal.panterasgym.clases

data class Rutina(
    var rNombre: String,
    var rDescripcion: String,
    var rCreacion: String,
    var rDuracion: String,
    var rEjercicios: List<Ejercicio> = listOf()
)