package com.proyectofinal.panterasgym.clases

class Calculadora {
    fun calcularIMC(altura: Float, peso: Float): Float {
        return peso / (altura * altura)
    }
}