package com.proyectofinal.panterasgym

import android.app.Application
import android.content.Intent
import android.widget.Toast

class Aplicacion : Application() {

    override fun onCreate() {
        super.onCreate()

        // Aquí puedes inicializar servicios o configuraciones globales
        startSensorService()
        //Toast.makeText(this, "Entro a app", Toast.LENGTH_SHORT).show()
    }

    private fun startSensorService() {
        // Inicia el servicio para manejar el sensor de luz
        val intent = Intent(this, LightSensorService::class.java)
        startService(intent)
    }
}