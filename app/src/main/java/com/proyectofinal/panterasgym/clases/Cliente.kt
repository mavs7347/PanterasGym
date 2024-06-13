package com.proyectofinal.panterasgym.clases

class Cliente {
    var cNombre: String = ""
        get() {return  field}
        set(value) {field = value}
    var cCorreo: String = ""
        get() {return field}
        set(value) {field = value}
    var cContrasena: String = ""
        get() {return  field}
        set(value) {field = value}
    var cEdad: Int = 0
        get() {return  field}
        set(value) {field = value}
    var cPeso: Float = 0.0f
        get() {return  field}
        set(value) {field = value}
    var cAltura: Float = 0.0f
        get() {return  field}
        set(value) {field = value}
    var cIndiceMasaCorporal: Float = 0.0f
        get() = Calculadora().calcularIMC(cAltura, cPeso)
    var cRecordar: Boolean = false
        get() {return  field}
        set(value) {field = value}
    var cRutinas: List<Rutina> = listOf()
        get() {return  field}
        set(value) { field = value }
}