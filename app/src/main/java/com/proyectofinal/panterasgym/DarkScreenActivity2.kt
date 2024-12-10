package com.proyectofinal.panterasgym

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DarkScreenActivity2 : AppCompatActivity() {

    private lateinit var lightReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_screen2)

        // Configurar el BroadcastReceiver
        lightReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val isDark = intent?.getBooleanExtra("dark_screen", true) ?: true
                if (!isDark) {
                    finish() // Cerrar la actividad si la luz vuelve a ser alta
                }
            }
        }

        // Registrar el receptor
        registerReceiver(lightReceiver, IntentFilter("com.proyectofinal.panterasgym.DARK_SCREEN"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(lightReceiver) // Liberar el receptor al destruir la actividad
    }
}
