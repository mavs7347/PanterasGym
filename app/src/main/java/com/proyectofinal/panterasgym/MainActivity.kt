package com.proyectofinal.panterasgym

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
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
        val appPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        if (appPreferences.contains("theme")) {
            if (appPreferences.getInt("theme", 0) == 1) {
                setTheme(R.style.AppTheme)
            } else if (appPreferences.getInt("theme", 0) == 2) {
                setTheme(R.style.AppThemeDark)
            } else if (appPreferences.getInt("theme", 0) == 3) {
                val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                when (nightModeFlags) {
                    Configuration.UI_MODE_NIGHT_YES -> setTheme(R.style.AppThemeDark)
                    Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> setTheme(R.style.AppTheme)
                }
            }
        } else {
            val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                Configuration.UI_MODE_NIGHT_YES -> setTheme(R.style.AppThemeDark)
                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> setTheme(R.style.AppTheme)
            }
        }
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