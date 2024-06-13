package com.proyectofinal.panterasgym

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.proyectofinal.panterasgym.acceso.LoginActivity
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Timer().schedule(object  : TimerTask() {
            override fun run() {
                if (usuarioIdentificado()) {
                    intent = Intent(applicationContext, MenuActivity::class.java)
                } else {
                    intent = Intent(applicationContext, LoginActivity::class.java)
                }
                startActivity(intent)
                finish()
            }
        }, 1000)
    }

    private fun usuarioIdentificado(): Boolean {
        val preferences: SharedPreferences = getSharedPreferences("Datos usuario", MODE_PRIVATE)
        return preferences.getBoolean("cRecordar", false)
    }
}