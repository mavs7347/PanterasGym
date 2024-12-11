package com.proyectofinal.panterasgym

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log

class LightSensorService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private var isDarkScreenActive = false // Bandera para evitar múltiples instancias

    override fun onCreate() {
        super.onCreate()

        // Inicializar el SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Registrar el sensor
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Log.e("LightSensorService", "Sensor de luz no disponible")
            stopSelf() // Detén el servicio si no hay sensor
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val lightLevel = it.values[0] // Nivel de luz en lux

            if (lightLevel < 0.5) {
                // Nivel de luz bajo
                if (!isDarkScreenActive) { // Solo lanzar si no está activa
                    isDarkScreenActive = true
                    openDarkScreen()
                }
            } else {
                // Nivel de luz alto
                if (isDarkScreenActive) {
                    closeDarkScreen()
                    isDarkScreenActive = false
                }
            }
        }
    }

    private fun openDarkScreen() {
        val intent = Intent(this, DarkScreenActivity2::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun closeDarkScreen() {
        // Enviar un Broadcast para cerrar la actividad
        val intent = Intent("com.proyectofinal.panterasgym.DARK_SCREEN")
        intent.putExtra("dark_screen", false)
        sendBroadcast(intent)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario manejar esto
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this) // Liberar el sensor
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}