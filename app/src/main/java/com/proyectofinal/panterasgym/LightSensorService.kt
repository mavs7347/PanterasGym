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
import android.view.View
import android.view.WindowManager

class LightSensorService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private var overlayView: View? = null
    private lateinit var windowManager: WindowManager

    override fun onCreate() {
        super.onCreate()

        // Inicializa el SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Inicializa el WindowManager
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Registrar el sensor
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            Log.e("LightSensorService", "Sensor de luz no disponible")
            stopSelf()
        }

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val lightLevel = it.values[0] // Nivel de luz en lux

            if (lightLevel < 3) {
                showDarkScreen() // Mostrar pantalla oscura
            } else {
                removeDarkScreen() // Eliminar pantalla oscura
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario manejar esto en este caso
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this) // Asegúrate de liberar el sensor
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Este servicio no requiere comunicación directa
    }

    private fun showDarkScreen() {
        val intent = Intent(this, DarkScreenActivity2::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_ANIMATION
        }
        startActivity(intent)
    }

    private fun removeDarkScreen() {
        // Opcionalmente, finaliza la actividad si es necesario
        val intent = Intent(this, DarkScreenActivity2::class.java)
        stopService(intent)
    }
}