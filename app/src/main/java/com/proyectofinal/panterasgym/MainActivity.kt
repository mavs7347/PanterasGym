package com.proyectofinal.panterasgym

import android.content.SharedPreferences
import android.os.Bundle
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.proyectofinal.panterasgym.acceso.LoginActivity
import com.proyectofinal.panterasgym.clases.Cliente

class MainActivity : AppCompatActivity() {
    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var appPreferences: SharedPreferences
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            // Configuración de tema
            appPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
            setThemeFromPreferences(appPreferences)

            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            clienteViewModel = ViewModelProvider(this).get(ClienteViewModel::class.java)

            verificarUsuarioRecordado()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error durante onCreate: ${e.message}")
        }
    }

    private fun setThemeFromPreferences(appPreferences: SharedPreferences) {
        try {
            val theme = appPreferences.getInt("theme", 0)
            when (theme) {
                1 -> {
                    Log.d("MainActivity", "Aplicando tema claro")
                    setTheme(R.style.AppTheme)
                }
                2 -> {
                    Log.d("MainActivity", "Aplicando tema oscuro")
                    setTheme(R.style.AppThemeDark)
                }
                3 -> setNightModeTheme()
                else -> {
                    Log.e("MainActivity", "Valor desconocido de tema: $theme. Usando tema predeterminado.")
                    setTheme(R.style.AppTheme)
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al configurar tema: ${e.message}")
        }
    }

    private fun setNightModeTheme() {
        try {
            val nightModeFlags = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
            when (nightModeFlags) {
                android.content.res.Configuration.UI_MODE_NIGHT_YES -> {
                    Log.d("MainActivity", "Aplicando tema oscuro (modo nocturno)")
                    setTheme(R.style.AppThemeDark)
                }
                android.content.res.Configuration.UI_MODE_NIGHT_NO -> {
                    Log.d("MainActivity", "Aplicando tema claro (modo diurno)")
                    setTheme(R.style.AppTheme)
                }
                else -> {
                    Log.w("MainActivity", "Modo nocturno indefinido. Usando tema claro por defecto.")
                    setTheme(R.style.AppTheme)
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al aplicar modo nocturno: ${e.message}")
        }
    }

    private fun verificarUsuarioRecordado() {
        db.collection("UsuariosRecordados").get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val usuarioRecordado = result.documents.first().toObject(Cliente::class.java)
                    clienteViewModel.cliente.value = usuarioRecordado
                    abrirMenu()
                } else {
                    abrirLogin()
                }
            }
            .addOnFailureListener { e ->
                Log.e("MainActivity", "Error al verificar usuario recordado: ${e.message}")
                abrirLogin()
            }
    }

    private fun abrirMenu() {
        Log.d("MainActivity", "Redirigiendo a MenuActivity")
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun abrirLogin() {
        Log.d("MainActivity", "Redirigiendo a LoginActivity")
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
